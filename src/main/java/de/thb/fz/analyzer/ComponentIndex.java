package de.thb.fz.analyzer;

import de.thb.fz.dsl.Component;
import java.util.HashMap;

public class ComponentIndex {

  /**
   * Ein Zuordnung von allen Klassennamen und Komponenten
   */
  private HashMap<String, Component> componentMap;
  /**
   * Ein Zuordnung von allen Klassen und Komponentenknoten
   */
  private HashMap<Class, ComponentNode> componentNodeMap;

  public ComponentIndex() {
    this.componentMap = new HashMap<>();
    this.componentNodeMap = new HashMap<>();
  }

  public Component findComponentByClass(Class cls) {
    return this.componentMap.get(cls.getName());
  }

  public HashMap<String, Component> getComponentMap() {
    return componentMap;
  }

  public void setComponentMap(HashMap<String, Component> componentMap) {
    this.componentMap = componentMap;
  }

  public HashMap<Class, ComponentNode> getComponentNodeMap() {
    return componentNodeMap;
  }

  public void setComponentNodeMap(
      HashMap<Class, ComponentNode> componentNodeMap) {
    this.componentNodeMap = componentNodeMap;
  }
}
