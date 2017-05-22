package de.fz.dsl;

import java.util.ArrayList;


public class Component {

  private String componentName;

  private ArrayList<Package> packages;

  private ArrayList<Class> interfaces;

  private ArrayList<Class> implementations;

  private ArrayList<Component> components;

  private ArrayList<Package> usages;


  public Component(String componentName) {
    this.componentName = componentName;
    this.packages = new ArrayList<Package>();
    this.usages = new ArrayList<Package>();
  }

  public ArrayList<Package> getConsist() {
    return packages;
  }

  public void setConsist(ArrayList<Package> consist) {
    this.packages = consist;
  }

  public ArrayList<Package> getUses() {
    return usages;
  }

  public void setUses(ArrayList<Package> uses) {
    this.usages = uses;
  }

  public Component addUses(Package component) {
    this.usages.add(component);
    return this;
  }

  public String getComponentName() {
    return componentName;
  }

  public void setComponentName(String componentName) {
    this.componentName = componentName;
  }
}
