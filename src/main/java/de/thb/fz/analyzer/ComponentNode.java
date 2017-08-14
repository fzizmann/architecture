package de.thb.fz.analyzer;

import de.thb.fz.dsl.Component;
import java.util.ArrayList;
import java.util.HashMap;

public class ComponentNode {

  // TODO entfernen der Sprachelemente aus der Datenstruktur, diese sollte vollständig in die DS übersetzt werden
  private Component component;
  private ArrayList<String> connection;
  private ArrayList<Class> classList;
  private ArrayList<Class> dependencyList;
  // TODO neue Strukturen
  private String componentName;
  private HashMap<Class, Class> definedConnections;
  private HashMap<Class, Class> connectioins;
  private HashMap<Class, Component> users;
  private HashMap<Class, Component> used;
  private ArrayList<ComponentNode> subComponents;
  private String type;


  public ComponentNode() {
    connection = new ArrayList<String>();
  }

  public ComponentNode addComponent(String name) {
    if (!name.equals(this.component.getComponentName())) {
      this.connection.add(name);
    }
    return this;
  }

  public void setComponent(Component component) {
    this.component = component;
  }

  public Component getComponent() {
    return component;
  }

  public ArrayList<String> getConnection() {
    return connection;
  }

  public void setConnection(ArrayList<String> connection) {
    this.connection = connection;
  }

  public ArrayList<Class> getClassList() {
    return classList;
  }

  public void setClassList(ArrayList<Class> classList) {
    this.classList = classList;
  }

  public ArrayList<Class> getDependencyList() {
    return dependencyList;
  }

  public void setDependencyList(ArrayList<Class> dependencyList) {
    this.dependencyList = dependencyList;
  }
}

