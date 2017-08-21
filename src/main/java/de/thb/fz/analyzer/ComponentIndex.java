package de.thb.fz.analyzer;

import de.thb.fz.dsl.Component;
import java.util.HashMap;

public class ComponentIndex {

  /**
   * Ein Zuordnung von allen Klassennamen und Komponenten
   */
  private HashMap<Class, Component> componentMap;

  public ComponentIndex() {
    this.componentMap = new HashMap<>();
  }

  public HashMap<Class, Component> getComponentMap() {
    return componentMap;
  }

  public void setComponentMap(HashMap<Class, Component> componentMap) {
    this.componentMap = componentMap;
  }
}
