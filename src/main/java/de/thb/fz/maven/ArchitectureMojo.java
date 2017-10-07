package de.thb.fz.maven;

import de.thb.fz.analyzer.ArchitectureAnalyser;
import de.thb.fz.dependency.DependencyLoader;
import de.thb.fz.dsl.Architecture;
import de.thb.fz.dsl.ArchitectureDescription;
import de.thb.fz.dsl.builder.ArchitectureBuilder;
import java.util.ArrayList;
import java.util.Set;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

@Mojo(name = "analyze", defaultPhase = LifecyclePhase.TEST)
public class ArchitectureMojo extends AbstractMojo {

  @Parameter(property = "architectureClass", required = true)
  private String architectureClass;
  @Parameter(property = "baseDir", required = true)
  private String baseDir;
  @Parameter(property = "strictInterface", required = false)
  private boolean strictInterface = false;

  public void execute() throws MojoExecutionException {
    ArchitectureAnalyser architectureAnalyser = new ArchitectureAnalyser();
    try {
      Class architectureClass = Class.forName(this.architectureClass);
      Object objectInstance = architectureClass.newInstance();
      if (objectInstance instanceof ArchitectureDescription) {
        Architecture architecture = ((ArchitectureDescription) objectInstance).defineArchitecture();
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
        Set<Class<?>> classes = new DependencyLoader().generateClassList(this.baseDir);
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

        architectureAnalyser.analyzeStyle(architecture).forEach(
            styleViolation -> getLog().info(styleViolation.getViolationMessage())
        );

        getLog().info("");
        getLog().info("Stilverletzungen:");
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