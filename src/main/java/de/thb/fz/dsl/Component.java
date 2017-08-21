package de.thb.fz.dsl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


public class Component {

  private String componentName;

  private ArrayList<String> structure;

  private ArrayList<Class> interfaces;

  private ArrayList<Class> implementations;

  private ArrayList<Component> subComponents;

  private String type;


  private HashMap<Class, Class> definedConnections;
  private HashMap<Class, Class> connectioins;
  private HashMap<Class, Component> users;
  private HashMap<Class, Component> used;

  public Component(String componentName) {
    this.structure = new ArrayList<String>();
    this.interfaces = new ArrayList<Class>();
    this.implementations = new ArrayList<Class>();
    this.subComponents = new ArrayList<Component>();
    this.componentName = componentName;
  }

  public static Component component(String ComponentName) {
    return new Component(ComponentName);
  }

  public Component structure(String... jPackage) {
    this.structure.addAll(Arrays.asList(jPackage));
    return this;
  }

  public Component implementations(Class... implementations) {
    this.implementations.addAll(Arrays.asList(implementations));
    return this;
  }

  public Component interfaces(Class... interfaces) {
    this.interfaces.addAll(Arrays.asList(interfaces));
    return this;
  }

  public Component subComponents(Component... components) {
    this.subComponents.addAll(Arrays.asList(components));
    return this;
  }

  public String getComponentName() {
    return componentName;
  }

  public ArrayList<String> getStructure() {
    return structure;
  }

  public ArrayList<Class> getInterfaces() {
    return interfaces;
  }

  public ArrayList<Class> getImplementations() {
    return implementations;
  }

  public ArrayList<Component> getSubComponents() {
    return subComponents;
  }

}
