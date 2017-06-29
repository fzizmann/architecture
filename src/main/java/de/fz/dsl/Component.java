package de.fz.dsl;

import java.util.ArrayList;
import java.util.Arrays;


public class Component {

  private String componentName;

  private ArrayList<JavaPackage> packages;

  private ArrayList<Class> interfaces;

  private ArrayList<Class> implementations;

  private ArrayList<Component> components;

  private ArrayList<JavaPackage> usages;

  public static Component component() {
    return new Component();
  }

  public Component packages(JavaPackage... packages) {
    this.packages.addAll(Arrays.asList(packages));
    return this;
  }

  public Component components(Component... components) {
    this.components.addAll(Arrays.asList(components));
    return this;
  }

  public Component implementations(Class... implementations) {
    this.implementations.addAll(Arrays.asList(implementations));
    return this;
  }

  public Component usages(JavaPackage... usages) {
    this.usages.addAll(Arrays.asList(usages));
    return this;
  }

  public Component interfaces(Class... interfaces) {
    this.interfaces.addAll(Arrays.asList(interfaces));
    return this;
  }
}
