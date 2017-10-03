package de.fz;

import de.thb.fz.analyzer.ArchitectureAnalyser;
import de.thb.fz.dependency.DependencyLoader;
import de.thb.fz.dsl.Architecture;
import de.thb.fz.dsl.ArchitectureDescription;
import de.thb.fz.dsl.builder.ArchitectureBuilder;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 *
 */
@Mojo(name = "analyze", defaultPhase = LifecyclePhase.TEST)
public class ArchitectureMojo extends AbstractMojo {

  @Parameter(property = "architectureClass", required = true)
  private String architectureClass;
  @Parameter(property = "baseDir", required = true)
  private String baseDir;

  public void execute() throws MojoExecutionException {
    ArchitectureAnalyser architectureAnalyser = new ArchitectureAnalyser();
    try {
      Class architectureClass = Class.forName(this.architectureClass);
      Object objectInstance = architectureClass.newInstance();
      if (objectInstance instanceof ArchitectureDescription) {
        Architecture architecture = ((ArchitectureDescription) objectInstance).defineArchitecture();
        new ArchitectureBuilder(new DependencyLoader()).accumulateArchitecture(architecture);

        architectureAnalyser.analyzeInterfaceAndImplementation(architecture).forEach(
            architectureViolation -> getLog().info(architectureViolation.toString())
        );
        architectureAnalyser.analyzeStyle(architecture).forEach(
            patternViolation -> getLog().info(patternViolation.getViolationMessage())
        );
        architectureAnalyser.analyzeWeights(architecture).forEach(
            s -> getLog().info(s)
        );
      }
    } catch (Exception e) {
      getLog().error(e.toString());
    }
    getLog().info("Architecture Class: " + architectureClass);
  }
}