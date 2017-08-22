package de.thb.fz.analyzer.builder;

import de.thb.fz.analyzer.ComponentIndex;
import de.thb.fz.dependency.DependencyLoader;
import de.thb.fz.dsl.Architecture;
import de.thb.fz.dsl.Component;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map.Entry;

/**
 * Diese Klasse erstellt den Komponentenbaum mittels der Architektur.
 */
public class ComponentBuilder {

  private DependencyLoader dependencyLoader;
  private ComponentIndex componentIndex;

  public ComponentBuilder(DependencyLoader dependencyLoader,
      ComponentIndex componentIndex) {
    this.dependencyLoader = dependencyLoader;
    this.componentIndex = componentIndex;
  }

  /**
   * Komponenten werden um alle Klassen ergäntzt aus denen sie bestehen.
   */
  public void findComponentClasses(Architecture architecture) {
    for (Component component : architecture.getComponents()) {
      for (String packageName : component.getStructure()) {
        component.getClasses().addAll(
            dependencyLoader.generateClassList(packageName));
      }
    }
  }

  public void findComponentUsages(Architecture architecture)
      throws IOException, ClassNotFoundException {
    for (Component component : architecture.getComponents()) {
      ArrayList<String> depList = new ArrayList<>();
      for (Class aClass : component.getClasses()) {
        depList.addAll(dependencyLoader.findDependenciesForClass(aClass.getName()));
        for (String dep : depList) {
          Component cp = this.findComponentByPackage(dep);
          if (cp != null && !cp.getComponentName().equals(component.getComponentName())) {
            component.getUses().put(aClass, cp);
            component.getUsed().put(Class.forName(dep), cp);
            if (!component.getConnection().containsKey(aClass)) {
              component.getConnection().put(aClass, new ArrayList<>());
            } else {
              ArrayList<Class> classes = component.getConnection().get(aClass);
              if (!classes.contains(Class.forName(dep))) {
                classes.add(Class.forName(dep));
              }
            }
          }
        }
      }
    }
  }

  /**
   * Sucht eine Componente anhand eines angegebenen Pakets.
   */
  private Component findComponentByPackage(String packageName) {
    for (Entry<Class, Component> entry : componentIndex.getComponentMap().entrySet()) {
      if (packageName.startsWith(entry.getKey().getName())) {
        return entry.getValue();
      }
    }
    return null;
  }

}
