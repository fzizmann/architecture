package de.thb.fz.dsl;

import de.thb.fz.style.Style;
import java.util.ArrayList;
import java.util.Arrays;

public class Architecture {

  private ArrayList<Component> components;
  private ArrayList<Style> styles;

  private Architecture() {
    this.components = new ArrayList<>();
    this.styles = new ArrayList<>();
  }

  public ArrayList<Component> getComponents() {
    return components;
  }

  public static Architecture architecture(Component... components) {
    return new Architecture().components(components);
  }

  public Architecture styles(Style... styles) {
    this.styles.addAll(Arrays.asList(styles));
    return this;
  }

  private Architecture components(Component... components) {
    this.components.addAll(Arrays.asList(components));
    return this;
  }

  public ArrayList<Style> getStyles() {
    return styles;
  }

  public void setStyles(ArrayList<Style> styles) {
    this.styles = styles;
  }
}
