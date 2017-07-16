package de.thb.fz.analyzer;

import java.util.ArrayList;

public class ComponentTree {

  private ArrayList<ComponentNode> componentList;

  public ComponentTree() {
    componentList = new ArrayList<ComponentNode>();
  }

  public ComponentTree addComponent(ComponentNode node) {
    this.componentList.add(node);
    return this;
  }

  public ArrayList<ComponentNode> getComponentList() {
    return componentList;
  }
}
