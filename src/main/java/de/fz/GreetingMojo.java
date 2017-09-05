package de.fz;

import java.io.File;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * Says "Hi" to the user.
 */
@Mojo(name = "sayhi")
public class GreetingMojo extends AbstractMojo {

  @Parameter(defaultValue = "${project.build.directory}", property = "outputDir", required = true)
  private File outputDirectory;

  public void execute() throws MojoExecutionException {
    getLog().info("Hello, world.");
  }
}