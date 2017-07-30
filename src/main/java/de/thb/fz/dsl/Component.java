package de.thb.fz.dsl;

import java.util.ArrayList;
import java.util.Arrays;


public class Component {

  private String componentName;

  private ArrayList<JavaPackage> structure;

  private ArrayList<Class> interfaces;

  private ArrayList<Class> implementations;

  private ArrayList<Component> subComponents;

  public Component(String componentName) {
    this.structure = new ArrayList<JavaPackage>();
    this.interfaces = new ArrayList<Class>();
    this.implementations = new ArrayList<Class>();
    this.subComponents = new ArrayList<Component>();
    this.componentName = componentName;
  }

  public static Component component(String ComponentName) {
    return new Component(ComponentName);
  }

  public Component structure(JavaPackage... javaPackage) {
    this.structure.addAll(Arrays.asList(javaPackage));
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

  public ArrayList<JavaPackage> getStructure() {
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
