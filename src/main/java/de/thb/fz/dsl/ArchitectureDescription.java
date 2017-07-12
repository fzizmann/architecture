package de.thb.fz.dsl;

import java.util.ArrayList;
import java.util.Arrays;

public class ArchitectureDescription {

  private ArrayList<Component> components;

  public ArchitectureDescription() {
    this.components = new ArrayList<Component>();
  }

  public ArrayList<Component> getComponents() {
    return components;
  }

  public static ArchitectureDescription architecture(Component... components) {
    return new ArchitectureDescription().components(components);
  }

  public ArchitectureDescription components(Component... components) {
    this.components.addAll(Arrays.asList(components));
    return this;
  }

}
