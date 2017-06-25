package de.fz.dsl;

import java.util.ArrayList;


public class Component {

  private String componentName;

  private ArrayList<Package> packages;

  private ArrayList<Class> interfaces;

  private ArrayList<Class> implementations;

  private ArrayList<Dependency> components;

  private ArrayList<Package> usages;


  public Component(String componentName) {
    this.componentName = componentName;
    this.packages = new ArrayList<Package>();
    this.usages = new ArrayList<Package>();
  }
}
