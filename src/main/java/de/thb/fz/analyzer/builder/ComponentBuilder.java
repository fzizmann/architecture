package de.thb.fz.analyzer.builder;

import de.thb.fz.analyzer.ComponentIndex;
import de.thb.fz.dependency.DependencyLoader;
import de.thb.fz.dsl.Component;
import java.util.HashSet;

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

  public void expandComponents() {
    this.findComponentClasses();
    this.findComponentUsages();
  }

  /**
   * Komponenten werden um alle Klassen ergäntzt aus denen sie bestehen.
   */
  private void findComponentClasses() {
    componentIndex.getComponentMap().forEach((aClass, component) -> component.getStructure()
        .forEach(packageName -> component.getClasses().addAll(
            dependencyLoader.generateClassList(packageName))));
  }

  /**
   * Komponentenklassen werden anhand ihrer Abhängikeiten zu anderen Komponenten aufgelöst.
   */
  private void findComponentUsages() {
    this.componentIndex.getComponentMap().forEach(
        (aClass, component) -> this.dependencyLoader.findDependenciesForClass(aClass.getName())
            .forEach(dependencyClass -> {
              Component dependencyComponent = this.componentIndex.getComponentMap()
                  .get(dependencyClass);
              if (dependencyComponent != null && !dependencyComponent.equals(component)) {
                component.getUsed().put(dependencyClass, dependencyComponent);

                component.getUses().putIfAbsent(aClass, new HashSet<>());
                component.getUses().computeIfPresent(aClass, (classKey, componentValues) -> {
                  componentValues.add(dependencyComponent);
                  return componentValues;
                });

                component.getConnection().putIfAbsent(aClass, new HashSet<>());
                component.getConnection().computeIfPresent(aClass, (classKey, classes) -> {
                  classes.add(dependencyClass);
                  return classes;
                });
              }
            }));
  }

}
