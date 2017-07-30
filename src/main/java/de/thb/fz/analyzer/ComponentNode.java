package de.thb.fz.analyzer;

import de.thb.fz.dsl.Component;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ComponentNode {

  private String componentname;
  private Component component;
  private ArrayList<String> connection;
  private ArrayList<ComponentConnection> connections;

  public void minimizeConnectionList() {
    Set<String> minimizedList = new HashSet<String>();
    minimizedList.addAll(this.connection);
    this.connection = new ArrayList<String>(minimizedList);
  }

  public ComponentNode() {
    connection = new ArrayList<String>();
    connections = new ArrayList<ComponentConnection>();
  }

  public ComponentNode addComponent(String name) {
    if (!name.equals(this.componentname)) {
      this.connection.add(name);
    }
    this.minimizeConnectionList();
    return this;
  }

  public ArrayList<String> getConnection() {
    return connection;
  }

  public String getComponentname() {
    return componentname;
  }

  public void setComponentname(String componentname) {
    this.componentname = componentname;
  }

  public void setConnection(ArrayList<String> connection) {
    this.connection = connection;
  }

  public ArrayList<ComponentConnection> getConnections() {
    return connections;
  }

  public void setConnections(ArrayList<ComponentConnection> connections) {
    this.connections = connections;
  }

  public Component getComponent() {
    return component;
  }

  public void setComponent(Component component) {
    this.component = component;
  }
}
