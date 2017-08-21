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


  private ArrayList<Class> classes;
  private HashMap<Class, Class> definedConnections;
  private HashMap<Class, Class> connectioins;
  private HashMap<Class, Component> users;
  private HashMap<Class, Component> uses;

  private Component(String componentName) {
    this.structure = new ArrayList<>();
    this.interfaces = new ArrayList<>();
    this.implementations = new ArrayList<>();
    this.subComponents = new ArrayList<>();
    this.componentName = componentName;
    this.classes = new ArrayList<>();
    this.uses = new HashMap<>();
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

  public ArrayList<Class> getClasses() {
    return classes;
  }

  public void setClasses(ArrayList<Class> classes) {
    this.classes = classes;
  }

  public HashMap<Class, Component> getUses() {
    return uses;
  }

  public void setUses(HashMap<Class, Component> uses) {
    this.uses = uses;
  }
}
