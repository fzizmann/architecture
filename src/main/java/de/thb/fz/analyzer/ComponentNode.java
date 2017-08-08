package de.thb.fz.analyzer;

import de.thb.fz.dsl.Component;
import java.util.ArrayList;

public class ComponentNode {

  private Component component;
  private ArrayList<String> connection;
  private ArrayList<Class> classList;
  private ArrayList<Class> dependencyList;
  private ArrayList<ComponentConnection> connections;

  public ComponentNode() {
    connection = new ArrayList<String>();
    connections = new ArrayList<ComponentConnection>();
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

  public ArrayList<ComponentConnection> getConnections() {
    return connections;
  }

  public void setConnections(ArrayList<ComponentConnection> connections) {
    this.connections = connections;
  }
}

