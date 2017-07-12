package de.thb.fz.dsl;

import java.util.ArrayList;
import java.util.Arrays;


public class Component {

  private String componentName;

  private ArrayList<JavaPackage> structure;

  private ArrayList<Class> interfaces;

  private ArrayList<Class> implementations;

  private ArrayList<Component> subComponents;

  public static Component component(String ComponentName) {
    return new Component(ComponentName);
  }

  public Component(String componentName) {
    this.componentName = componentName;
  }

  public String getComponentName() {
    return componentName;
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

  public Component components(Component... components) {
    this.subComponents.addAll(Arrays.asList(components));
    return this;
  }
}
