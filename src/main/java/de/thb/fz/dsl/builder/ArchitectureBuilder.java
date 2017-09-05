package de.thb.fz.dsl.builder;

import de.thb.fz.dependency.DependencyLoader;
import de.thb.fz.dsl.Architecture;
import de.thb.fz.dsl.Component;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Diese Klasse erstellt den Komponentenbaum mittels der Architektur.
 */
public class ArchitectureBuilder {

  private DependencyLoader dependencyLoader;

  public ArchitectureBuilder(DependencyLoader dependencyLoader) {
    this.dependencyLoader = dependencyLoader;
  }

  public void accumulateArchitecture(Architecture architecture) {
    this.buildComponentIndex(architecture);
    this.findComponentClasses(architecture);
    this.findComponentUsages(architecture);
  }

  /**
   * Komponenten werden um alle Klassen ergäntzt aus denen sie bestehen.
   */
  private void findComponentClasses(Architecture architecture) {
    architecture.getComponentIndex().forEach((aClass, component) -> component.getStructure()
        .forEach(packageName -> component.getClasses().addAll(
            dependencyLoader.generateClassList(packageName))));
  }

  /**
   * Komponentenklassen werden anhand ihrer Abhängikeiten zu anderen Komponenten aufgelöst.
   */
  private void findComponentUsages(Architecture architecture) {
    architecture.getComponentIndex().forEach(
        (aClass, component) -> this.dependencyLoader.findDependenciesForClass(aClass.getName())
            .forEach(dependencyClass -> {
              Component dependencyComponent = architecture.getComponentIndex().get(dependencyClass);
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


  private void buildComponentIndex(Architecture architecture) {
    HashMap<Class, Component> componentIndex = new HashMap<>();

    architecture.getComponents().forEach(component ->
        this.findSubComponents(component, componentIndex));

    architecture.setComponentIndex(componentIndex);
  }


  private void findSubComponents(Component component, HashMap<Class, Component> componentIndex) {
    component.getSubComponents()
        .forEach(subComponent -> this.findSubComponents(subComponent, componentIndex));

    component.getStructure().forEach(packageName ->
        dependencyLoader.generateClassList(packageName).forEach(jClass ->
            componentIndex.putIfAbsent(jClass, component)));
  }

}
