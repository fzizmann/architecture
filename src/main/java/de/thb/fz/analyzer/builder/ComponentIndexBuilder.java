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

  public ComponentIndex createComponentIndex(Architecture architecture) {
    ComponentIndex componentIndex = new ComponentIndex();
    for (Component component : architecture.getComponents()) {
      for (String packageName : component.getStructure()) {
        for (Class jClass : dependencyLoader.generateClassList(packageName)) {
          componentIndex.getComponentMap().put(jClass, component);
        }
      }
    }
    return componentIndex;
  }


}
