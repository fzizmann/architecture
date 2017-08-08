package de.thb.fz.analyzer.builder;

import de.thb.fz.dependency.DependencyLoader;

/**
 * Diese Klasse erstellt den Komponentenbaum mittels der Architektur.
 */
public class ComponentTreeBuilder {

  private DependencyLoader dependencyLoader;

  public ComponentTreeBuilder(DependencyLoader dependencyLoader) {
    this.dependencyLoader = dependencyLoader;
  }

}
