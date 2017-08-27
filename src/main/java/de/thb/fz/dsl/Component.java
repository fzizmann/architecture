package de.thb.fz.dsl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;


public class Component {

  /**
   * Komponentenname.
   */
  private String componentName;
  /**
   * Pakete in dieser Komponente
   */
  private ArrayList<String> structure = new ArrayList<>();
  /**
   * Angebotente Schnittstellen dieser Komponente
   */
  private ArrayList<Class> interfaces = new ArrayList<>();
  /**
   * Implementierte dieser Komponente von anderen Komponenten.
   */
  private ArrayList<Class> implementations = new ArrayList<>();
  /**
   * Subkomponenten dieser Komponente.
   */
  private ArrayList<Component> subComponents = new ArrayList<>();
  /**
   * Typ der Kopmonente.
   */
  private String type;
  /**
   * Klassen die in dier Komponente enthalten sind.
   */
  private ArrayList<Class> classes = new ArrayList<>();
  /**
   * Eine Klasse aus diesem Paket nutzt die gemappten Klassen.
   */
  private HashMap<Class, HashSet<Class>> connection = new HashMap<>();
  /**
   * Diese Klasse wird aus dieser Komponente genutzt.
   */
  private HashMap<Class, Component> used = new HashMap<>();
  /**
   * Diese Klasse nutzt Komponenten nutzt Klassen in der gemappten Komponente.
   */
  private HashMap<Class, HashSet<Component>> uses = new HashMap<>();

  private Component(String componentName) {
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

  public Component type(String type) {
    this.type = type;
    return this;
  }

  public String getComponentName() {
    return componentName;
  }

  public ArrayList<String> getStructure() {
    return structure;
  }

  public ArrayList<Class> getClasses() {
    return classes;
  }

  public HashMap<Class, HashSet<Component>> getUses() {
    return uses;
  }

  public HashMap<Class, Component> getUsed() {
    return used;
  }

  public HashMap<Class, HashSet<Class>> getConnection() {
    return connection;
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

  public String getType() {
    return type;
  }

  @Override
  public String toString() {
    return this.getComponentName();
  }
}
