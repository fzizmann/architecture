package de.thb.fz.maven;

import de.thb.fz.analyzer.ArchitectureAnalyser;
import de.thb.fz.dependency.DependencyLoader;
import de.thb.fz.dsl.Architecture;
import de.thb.fz.dsl.ArchitectureDescription;
import de.thb.fz.dsl.builder.ArchitectureBuilder;
import de.thb.fz.violation.ArchitectureViolation;
import de.thb.fz.violation.UndefiendClassViolation;
import de.thb.fz.violation.UnusedInterfaceViolation;
import de.thb.fz.violation.Violation;
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
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

  public void execute() throws MojoExecutionException, MojoFailureException {
    ArchitectureAnalyser architectureAnalyser = new ArchitectureAnalyser();
    boolean violationExist = false;
    try {
      List runtimeClasspathElements = ((MavenProject) this.getPluginContext().get("project"))
          .getRuntimeClasspathElements();

      URL[] runtimeUrls = new URL[runtimeClasspathElements.size()];
      for (int i = 0; i < runtimeClasspathElements.size(); i++) {
        String element = (String) runtimeClasspathElements.get(i);
        runtimeUrls[i] = new File(element).toURI().toURL();
      }

      ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
      URLClassLoader newLoader = new URLClassLoader(runtimeUrls,
          contextClassLoader);
      Thread.currentThread().setContextClassLoader(newLoader);
      Object architectureClass = newLoader.loadClass(this.architectureClass).newInstance();

      if (architectureClass instanceof ArchitectureDescription) {
        Architecture architecture = ((ArchitectureDescription) architectureClass)
            .defineArchitecture();
        new ArchitectureBuilder(new DependencyLoader()).accumulateArchitecture(architecture);

        getLog().info("Komponentenverletzungen:");
        getLog().info("");
        ArrayList<ArchitectureViolation> architectureViolations = architectureAnalyser
            .analyzeInterfaceAndImplementation(architecture);
        architectureViolations.forEach(
            architectureViolation -> getLog().info(architectureViolation.toString())
        );

        getLog().info("");
        getLog().info("Nicht genutzte Interfaces:");
        getLog().info("");
        ArrayList<UnusedInterfaceViolation> unusedInterfaceViolations = architectureAnalyser
            .analyzeUnusedInterfaces(architecture);
        unusedInterfaceViolations.forEach(
            unusedInterfaceViolation -> getLog().info(unusedInterfaceViolation.toString())
        );

        getLog().info("");
        getLog().info("Nicht definierte Klassen:");
        getLog().info("");
        Set<Class<?>> classes = new DependencyLoader().generateClassList(this.basePackage);
        ArrayList<UndefiendClassViolation> undefiendClassViolations = architectureAnalyser
            .analyzeUndefinedClasses(architecture, new ArrayList<>(classes));
        undefiendClassViolations
            .forEach(
                unusedInterfaceViolation -> getLog().info(unusedInterfaceViolation.toString())
            );

        getLog().info("");
        getLog().info("Regelverletzungen:");
        getLog().info("");
        ArrayList<Violation> ruleViolations = architectureAnalyser.analyzeRules(architecture);
        ruleViolations.forEach(
            ruleViolation -> getLog().info(ruleViolation.getViolationMessage())
        );

        getLog().info("");
        getLog().info("Stilverletzungen:");
        getLog().info("");
        ArrayList<Violation> styleViolations = architectureAnalyser.analyzeStyle(architecture);
        styleViolations.forEach(
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
          ArrayList<Violation> interfaceViolations = architectureAnalyser
              .checkInterfaces(architecture);
          interfaceViolations.forEach(
              violation -> getLog().info(violation.getViolationMessage())
          );
          if (!interfaceViolations.isEmpty()) {
            violationExist = true;
          }
        }
        if (!styleViolations.isEmpty() ||
            !architectureViolations.isEmpty() ||
            !unusedInterfaceViolations.isEmpty() ||
            !undefiendClassViolations.isEmpty() ||
            !ruleViolations.isEmpty()) {
          violationExist = true;
        }
      }
    } catch (Exception e) {
      getLog().error(e.toString());
    }
    getLog().info("Architecture Class: " + architectureClass);

    if (violationExist) {
      throw new MojoFailureException("Es sind nicht behandelte Architekturfehler aufgetreten.");
    }
  }
}