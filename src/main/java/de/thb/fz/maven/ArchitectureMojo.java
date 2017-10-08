package de.thb.fz.maven;

import de.thb.fz.analyzer.ArchitectureAnalyser;
import de.thb.fz.dependency.DependencyLoader;
import de.thb.fz.dsl.Architecture;
import de.thb.fz.dsl.ArchitectureDescription;
import de.thb.fz.dsl.builder.ArchitectureBuilder;
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;

@Mojo(name = "analyze", defaultPhase = LifecyclePhase.TEST, requiresDependencyResolution = ResolutionScope.TEST)
public class ArchitectureMojo extends AbstractMojo {

  @Parameter(property = "architectureClass", required = true)
  private String architectureClass;
  @Parameter(property = "basePackage", required = true)
  private String basePackage;
  @Parameter(property = "strictInterface")
  private boolean strictInterface;

  public void execute() throws MojoExecutionException {
    ArchitectureAnalyser architectureAnalyser = new ArchitectureAnalyser();
    try {
      List runtimeClasspathElements = ((MavenProject) this.getPluginContext().get("project"))
          .getRuntimeClasspathElements();

      URL[] runtimeUrls = new URL[runtimeClasspathElements.size()];
      for (int i = 0; i < runtimeClasspathElements.size(); i++) {
        String element = (String) runtimeClasspathElements.get(i);
        runtimeUrls[i] = new File(element).toURI().toURL();
      }

      URLClassLoader newLoader = new URLClassLoader(runtimeUrls,
          Thread.currentThread().getContextClassLoader());
      Object architectureClass = newLoader.loadClass(this.architectureClass).newInstance();

      if (architectureClass instanceof ArchitectureDescription) {
        Architecture architecture = ((ArchitectureDescription) architectureClass)
            .defineArchitecture();
        new ArchitectureBuilder(new DependencyLoader()).accumulateArchitecture(architecture);

        getLog().info("Komponentenverletzungen:");
        getLog().info("");
        architectureAnalyser.analyzeInterfaceAndImplementation(architecture).forEach(
            architectureViolation -> getLog().info(architectureViolation.toString())
        );

        getLog().info("");
        getLog().info("Nicht genutzte Interface:");
        getLog().info("");
        architectureAnalyser.analyzeUnusedInterfaces(architecture).forEach(
            unusedInterfaceViolation -> getLog().info(unusedInterfaceViolation.toString())
        );

        getLog().info("");
        getLog().info("Nicht definierte Klassen:");
        getLog().info("");
        Set<Class<?>> classes = new DependencyLoader().generateClassList(this.basePackage);
        architectureAnalyser.analyzeUndefinedClasses(architecture, new ArrayList<>(classes))
            .forEach(
                unusedInterfaceViolation -> getLog().info(unusedInterfaceViolation.toString())
            );

        getLog().info("");
        getLog().info("Regelverletzungen:");
        getLog().info("");
        architectureAnalyser.analyzeRules(architecture).forEach(
            ruleViolation -> getLog().info(ruleViolation.getViolationMessage())
        );

        getLog().info("");
        getLog().info("Stilverletzungen:");
        getLog().info("");
        architectureAnalyser.analyzeStyle(architecture).forEach(
            styleViolation -> getLog().info(styleViolation.getViolationMessage())
        );

        getLog().info("");
        getLog().info("Graphiz-Architekturdefinition:");
        getLog().info("");
        getLog().info(architectureAnalyser.analyzeWeights(architecture));

        if (this.strictInterface) {
          getLog().info("");
          getLog().info("Klassen die als Interface definiert wurden:");
          getLog().info("");
          architectureAnalyser.checkInterfaces(architecture).forEach(
              violation -> getLog().info(violation.getViolationMessage())
          );
        }

      }
    } catch (Exception e) {
      getLog().error(e.toString());
    }
    getLog().info("Architecture Class: " + architectureClass);
  }
}