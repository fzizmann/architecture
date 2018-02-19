package de.thb.fz.dsl;

import de.thb.fz.dsl.style.pattern.Pattern;
import de.thb.fz.dsl.style.rule.Rule;
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
  private ArrayList<Pattern> patterns;
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
    this.patterns = new ArrayList<>();
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

  public Architecture pattern(Pattern... patterns) {
    this.patterns.addAll(Arrays.asList(patterns));
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

  public ArrayList<Pattern> getPatterns() {
    return patterns;
  }

  public ArrayList<Rule> getRules() {
    return rules;
  }
}
