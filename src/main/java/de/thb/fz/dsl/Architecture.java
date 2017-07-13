package de.thb.fz.dsl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class Architecture {

  private ArrayList<Component> components;
  private Map<String, Component> packageMap;

  public Architecture() {
    this.components = new ArrayList<Component>();
  }

  public ArrayList<Component> getComponents() {
    return components;
  }

  public static Architecture architecture(Component... components) {
    Architecture architecture = new Architecture().components(components);
    architecture.buildPackageComponentMap();
    return architecture;
  }

  public Architecture components(Component... components) {
    this.components.addAll(Arrays.asList(components));
    return this;
  }

  /**
   * Erstellt aus den gegebenen Komponenten eine Liste zur leichteren Ortung einer Komponente
   * anhand eiens Paketbezeichners.
   */
  public void buildPackageComponentMap() {
    for (Component component : components) {
      for (JavaPackage packageString : component.getStructure()) {
        packageMap.put(packageString.getPackageName(), component);
      }
    }
  }

  /**
   * Sucht eine Componente anhand einen angegebenen Pakets.
   * //TODO implementieren
   */
  public Component findComponentByPackage(String packageName) {
    return new Component("");
  }

}
