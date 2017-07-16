package de.thb.fz.analyzer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ComponentNode {

  private String componentname;
  private ArrayList<String> connection;

  public void minimizeConnectionList() {
    Set<String> minimizedList = new HashSet<String>();
    minimizedList.addAll(this.connection);
    this.connection = new ArrayList<String>(minimizedList);
  }

  public ComponentNode() {
    connection = new ArrayList<String>();
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
}
