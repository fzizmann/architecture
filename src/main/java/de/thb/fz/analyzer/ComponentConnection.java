package de.thb.fz.analyzer;


public class ComponentConnection {

  /**
   * Zielklasse der Verbindung
   */
  private Class targetClass;
  /**
   * Quellklasse der Verbindung
   */
  private Class sourceClass;
  /**
   * Diese Verbindung ist erlaubt
   */
  private Boolean permitted;

  public ComponentConnection(Class targetClass, Class sourceClass, Boolean permitted) {
    this.targetClass = targetClass;
    this.sourceClass = sourceClass;
    this.permitted = permitted;
  }
}
