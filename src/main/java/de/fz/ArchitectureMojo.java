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

  public void execute() throws MojoExecutionException {
    ArchitectureAnalyser architectureAnalyser = new ArchitectureAnalyser();
    try {
      Class architectureClass = Class.forName(this.architectureClass);
      Object objectInstance = architectureClass.newInstance();
      if (objectInstance instanceof ArchitectureDescription) {
        Architecture architecture = ((ArchitectureDescription) objectInstance).defineArchitecture();
        new ArchitectureBuilder(new DependencyLoader()).accumulateArchitecture(architecture);
        architectureAnalyser.analyzeInterfaceAndImplementation(architecture).forEach(
            s -> getLog().info(s)
        );
        architectureAnalyser.analyzeConnection(architecture).forEach(
            s -> getLog().info(s)
        );
        architectureAnalyser.analyzeStyle(architecture).forEach(
            s -> getLog().info(s)
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