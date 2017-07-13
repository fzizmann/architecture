package de.thb.fz.dsl;

import java.util.ArrayList;
import java.util.Arrays;

public class Architecture {

  private ArrayList<Component> components;

  public Architecture() {
    this.components = new ArrayList<Component>();
  }

  public ArrayList<Component> getComponents() {
    return components;
  }

  public static Architecture architecture(Component... components) {
    return new Architecture().components(components);
  }

  public Architecture components(Component... components) {
    this.components.addAll(Arrays.asList(components));
    return this;
  }

}
