package de.thb.fz.analyzer;

import de.thb.fz.dsl.Architecture;
import java.util.ArrayList;

public class ComponentRoot {

  /**
   * Die definierte Architektur
   */
  private Architecture architecture;
  /**
   * Die Liste der definierten Komponenten und wird aus der Architektur erstellt.
   */
  private ArrayList<ComponentNode> componentList;

  public ComponentRoot(Architecture architecture) {
    componentList = new ArrayList<>();
    this.architecture = architecture;
  }

  public ComponentRoot addComponent(ComponentNode node) {
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
}
