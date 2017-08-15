package de.thb.fz.analyzer;

import de.thb.fz.dsl.Component;
import java.util.HashMap;

public class ComponentIndex {

  /**
   * Ein Zuordnung von allen Klassen und Komponenten
   */
  private HashMap<String, Component> componentMap;

  public ComponentIndex() {
    this.componentMap = new HashMap<>();
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
}
