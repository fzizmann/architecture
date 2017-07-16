package de.thb.fz.dsl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class Architecture {

  private ArrayList<Component> components;
  private Map<String, Component> packageMap;

  public Architecture() {
    this.packageMap = new HashMap<String, Component>();
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
   */
  public Component findComponentByPackage(String packageName) {
    Iterator it = this.packageMap.entrySet().iterator();
    while (it.hasNext()) {
      Entry pair = (Entry) it.next();
      if (packageName.startsWith((String) pair.getKey())) {
        return (Component) pair.getValue();
      }
//      it.remove();
    }
    return null;
  }

  public void setComponents(ArrayList<Component> components) {
    this.components = components;
  }

  public Map<String, Component> getPackageMap() {
    return packageMap;
  }

  public void setPackageMap(Map<String, Component> packageMap) {
    this.packageMap = packageMap;
  }
}
