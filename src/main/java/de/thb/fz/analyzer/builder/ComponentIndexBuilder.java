package de.thb.fz.analyzer.builder;

import de.thb.fz.analyzer.ComponentIndex;
import de.thb.fz.dependency.DependencyLoader;
import de.thb.fz.dsl.Architecture;
import de.thb.fz.dsl.Component;

public class ComponentIndexBuilder {


  private DependencyLoader dependencyLoader;

  public ComponentIndexBuilder(DependencyLoader dependencyLoader) {
    this.dependencyLoader = dependencyLoader;
  }

  public ComponentIndex buildComponentIndex(Architecture architecture) {
    ComponentIndex componentIndex = new ComponentIndex();

    architecture.getComponents().forEach(component ->
        this.findSubComponents(component, componentIndex));

    return componentIndex;
  }


  private void findSubComponents(Component component, ComponentIndex componentIndex) {
    component.getSubComponents()
        .forEach(subComponent -> this.findSubComponents(subComponent, componentIndex));

    component.getStructure().forEach(packageName ->
        dependencyLoader.generateClassList(packageName).forEach(jClass ->
            componentIndex.getComponentMap().putIfAbsent(jClass, component)));
  }


}
