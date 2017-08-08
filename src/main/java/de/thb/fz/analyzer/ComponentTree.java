package de.thb.fz.analyzer;

import de.thb.fz.dsl.Architecture;
import de.thb.fz.dsl.Component;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ComponentTree {

  /**
   * Die definierte Architektur
   */
  private Architecture architecture;
  /**
   * Die Liste der definierten Komponenten und wird aus der Architektur erstellt.
   */
  private ArrayList<ComponentNode> componentList;
  /**
   * Ein Zuordnung von Paket und Komponente
   */
  private Map<String, Component> packageMap;

  public ComponentTree(Architecture architecture) {
    componentList = new ArrayList<ComponentNode>();
    this.packageMap = new HashMap<String, Component>();
    this.architecture = architecture;
  }

  public ComponentTree addComponent(ComponentNode node) {
    this.componentList.add(node);
    return this;
  }

  public Architecture getArchitecture() {
    return architecture;
  }

  public void setArchitecture(Architecture architecture) {
    this.architecture = architecture;
  }

  public ArrayList<ComponentNode> getComponentList() {
    return componentList;
  }

  public void setComponentList(ArrayList<ComponentNode> componentList) {
    this.componentList = componentList;
  }

  public Map<String, Component> getPackageMap() {
    return packageMap;
  }

  public void setPackageMap(Map<String, Component> packageMap) {
    this.packageMap = packageMap;
  }
}
