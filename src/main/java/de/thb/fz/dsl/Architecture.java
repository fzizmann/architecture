package de.thb.fz.dsl;

import de.thb.fz.style.pattern.Style;
import de.thb.fz.style.rule.Rule;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Architecture {

  /**
   * Komponenten in dieser Architektur
   */
  private ArrayList<Component> components;
  /**
   * In der Architektur werwendete Stile
   */
  private ArrayList<Style> styles;
  /**
   * Ein Zuordnung von allen Klassennamen und Komponenten
   */
  private HashMap<Class, Component> componentIndex;
  /**
   * Regeln der für die Architektur
   */
  private ArrayList<Rule> rules;

  private Architecture() {
    this.components = new ArrayList<>();
    this.styles = new ArrayList<>();
    this.rules = new ArrayList<>();
  }

  public HashMap<Class, Component> getComponentIndex() {
    return componentIndex;
  }

  public void setComponentIndex(HashMap<Class, Component> componentIndex) {
    this.componentIndex = componentIndex;
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

  public Architecture rules(Rule... rules) {
    this.rules.addAll(Arrays.asList(rules));
    return this;
  }

  private Architecture components(Component... components) {
    this.components.addAll(Arrays.asList(components));
    return this;
  }

  public ArrayList<Style> getStyles() {
    return styles;
  }

  public ArrayList<Rule> getRules() {
    return rules;
  }
}
