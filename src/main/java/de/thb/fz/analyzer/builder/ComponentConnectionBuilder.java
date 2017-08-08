package de.thb.fz.analyzer.builder;

import de.thb.fz.dependency.DependencyLoader;

/**
 * Erstellt einen Komponentenverbindung.
 */
public class ComponentConnectionBuilder {

  private DependencyLoader dependencyLoader;

  public ComponentConnectionBuilder(DependencyLoader dependencyLoader) {
    this.dependencyLoader = dependencyLoader;
  }

  /**
   * Erstellt eine Liste von Klassen die die Zielkomponente benutzt.
   */
  public void buildUserList() {

  }

  /**
   * Erstellt eine Liste von Klassen die in der Zielkomponente benutzt werden.
   */
  public void buildUsesList() {

  }

}

