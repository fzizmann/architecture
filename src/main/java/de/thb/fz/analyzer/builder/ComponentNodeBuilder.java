package de.thb.fz.analyzer.builder;

import de.thb.fz.analyzer.ComponentNode;
import de.thb.fz.dependency.DependencyLoader;
import de.thb.fz.dsl.Component;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Diese Klasse erstellt Komponentenknoten, sie lagert so die Logik zur Erstellung der Knoten aus
 * der Klasse aus.
 */
public class ComponentNodeBuilder {

  private DependencyLoader dependencyLoader;

  public ComponentNodeBuilder(DependencyLoader dependencyLoader) {
    this.dependencyLoader = dependencyLoader;
  }

  public ComponentNode buildComponentNode(Component component) {
    ComponentNode componentNode = new ComponentNode();
    componentNode.setComponent(component);
    this.buildClassList(componentNode);
    this.buildDependencyList(componentNode);
    return componentNode;
  }

  /**
   * Erstellt eine Liste der Klassen die in dieser Komponente enthalten sind.
   */
  public void buildClassList(ComponentNode componentNode) {
    ArrayList<String> javaPackages = componentNode.getComponent().getStructure();
    //TODO DependencyLoader.generateClassList in ArrayList<Class> umbauen
    for (String jPackage : javaPackages) {
      //laden der Klassen in einem Package
      File dir = new File(jPackage.replace(".", "\\"));
//      componentNode.setClassList(DependencyLoader
//          .generateClassList(dir));
    }
  }

  /**
   * Erstellt eine Liste der Klassen, die von dieser Komponente genutzt werden.
   */
  public void buildDependencyList(ComponentNode componentNode) {
    for (Class jClass : componentNode.getClassList()) {
      //TODO depList in ArrayList<Class> umbauen
      ArrayList<String> depList = new ArrayList<>();
      //laden der Abhängigkeiten
      String shortclass = jClass.getName().replace("\\", ".").replace(".class", "");
      try {
        depList.addAll(DependencyLoader.findDependenciesForClass(shortclass));
      } catch (IOException e) {
        e.printStackTrace();
      }
//      componentNode.setDependencyList(depList);
    }
  }

  /**
   * Minimiert die Liste der Klassen die in einer Komponente enthalten sind um die, die nicht
   * Klassen von anderen Komponenten benutzt.
   */
  public void minimizeClassList(ComponentNode componentNode) {
    Set<String> minimizedList = new HashSet<String>();
//    minimizedList.addAll(componentNode.getClassList());
//    componentNode.setClassList(new ArrayList<String>(minimizedList));
  }

  /**
   * Minimiert die Liste der Klassen die eine Komponente benutzt um die, die nicht in anderen
   * Komponenten enthalten sind.
   */
  public void minimizeDependencyList(ComponentNode componentNode) {

  }

  public void buildConnections(ComponentNode componentNode) {

  }
}
