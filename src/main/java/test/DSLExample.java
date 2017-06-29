package test;

import static de.fz.dsl.Component.component;
import static de.fz.dsl.JavaPackage.javaPackage;

import de.fz.analyzer.Analyzer;
import de.fz.dsl.ArchitectureDescription;
import de.fz.dsl.Component;
import java.util.Arrays;

/**
 * Example Class for Architecture
 */
public class DSLExample extends ArchitectureDescription {


  private Component subComponent = component()
      .packages(
          javaPackage("de.fz.first.*"),
          javaPackage("de.fz.second.*")
      )
      .interfaces(
          EntryPoint.class,
          Analyzer.class
      )
      .implementations(
          EntryPoint.class,
          Analyzer.class);

  private Component superComponent = component()
      .packages(
          javaPackage("test.test1.*")
      )
      .interfaces(
          Arrays.class
      )
      .implementations(
          String.class
      )
      .components(
          subComponent
      );

  public void architectureDefinition() {
    Component subComponent = component()
        .packages(
            javaPackage("de.fz.first.*"),
            javaPackage("de.fz.second.*")
        )
        .interfaces(
            EntryPoint.class,
            Analyzer.class
        )
        .implementations(
            EntryPoint.class,
            Analyzer.class);

    Component superComponent = component()
        .packages(
            //Todo Pakte literale durch automatisch vervollständigende Elemente ersetzen
            javaPackage("test.test1.*")
        )
        .interfaces(
            Arrays.class
        )
        .implementations(
            String.class
        )
        .components(
            subComponent

        );

  }
}
