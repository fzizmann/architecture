package de.thb.fz.dsl;

import de.thb.fz.dsl.type.ComponentType;
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
  private ArrayList<String> structure;
  /**
   * Angebotente Schnittstellen dieser Komponente
   */
  private ArrayList<Class> interfaces;
  /**
   * Implementierte dieser Komponente von anderen Komponenten.
   */
  private ArrayList<Class> implementations;
  /**
   * Subkomponenten dieser Komponente.
   */
  private ArrayList<Component> subComponents;
  /**
   * Typ der Kopmonente.
   */
  private ComponentType type;
  /**
   * Klassen die in dier Komponente enthalten sind.
   */
  private ArrayList<Class> classes;
  /**
   * Eine Klasse aus diesem Paket nutzt die gemappten Klassen.
   */
  private HashMap<Class, HashSet<Class>> connection;
  /**
   * Diese Klasse wird aus dieser Komponente genutzt.
   */
  private HashMap<Class, Component> used;
  /**
   * Diese Klasse nutzt Komponenten nutzt Klassen in der gemappten Komponente.
   */
  private HashMap<Class, HashSet<Component>> uses;

  private Component(String componentName) {
    this.structure = new ArrayList<>();
    this.interfaces = new ArrayList<>();
    this.implementations = new ArrayList<>();
    this.subComponents = new ArrayList<>();
    this.componentName = componentName;
    this.classes = new ArrayList<>();
    this.uses = new HashMap<>();
    this.used = new HashMap<>();
    this.connection = new HashMap<>();
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

  public Component type(ComponentType type) {
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

  @Override
  public String toString() {
    return this.getComponentName();
  }
}
