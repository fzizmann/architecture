package de.thb.fz.analyzer;

import de.thb.fz.dependency.DependencyLoader;
import de.thb.fz.dsl.Architecture;
import de.thb.fz.dsl.Component;
import de.thb.fz.dsl.JavaPackage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class ComponentTree {

  private Architecture architecture;
  private ArrayList<ComponentNode> componentList;
  private Map<String, Component> packageMap;

  public ComponentTree(Architecture architecture) {
    componentList = new ArrayList<ComponentNode>();
    this.packageMap = new HashMap<String, Component>();
    this.architecture = architecture;
    this.buildPackageComponentMap();
  }

  public void buildComponentList() {
    for (Component comp : architecture.getComponents()) {
      ComponentNode e = new ComponentNode();
//      e.setComponent();
      componentList.add(e);
    }
  }

  public void buildComponentTree(String basePath) {
    Iterator it = packageMap.entrySet().iterator();

    while (it.hasNext()) {
      Entry pair = (Entry) it.next();
      ComponentNode node = new ComponentNode();
      node.setComponent((Component) pair.getValue());
      String pathname = basePath + (String) pair.getKey();

      //laden der Klassen in einem Package
      ArrayList<String> classList = DependencyLoader
          .generateClassList(new File(pathname.replace(".", "\\")));
      for (String jClass : classList) {
        findComponentByClassString(basePath, node, jClass);
      }
      this.addComponent(node);
    }
  }

  private void findComponentByClassString(String basePath, ComponentNode node, String jClass) {
    ArrayList<String> depList = new ArrayList<String>();
    try {
      //laden der Abhängigkeiten
      String shortclass = jClass.replace(basePath, "").replace("\\", ".").replace(".class", "");
      depList.addAll(DependencyLoader.findDependenciesForClass(shortclass));
      for (String dep : depList) {
        Component cp = this.findComponentByPackage(dep);
        if (cp != null) {
          node.addComponent(cp.getComponentName());
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Erstellt aus den gegebenen Komponenten eine Liste zur leichteren Ortung einer Komponente anhand
   * eiens Paketbezeichners.
   */
  public void buildPackageComponentMap() {
    for (Component component : architecture.getComponents()) {
      for (JavaPackage packageString : component.getStructure()) {
        packageMap.put(packageString.getPackageName(), component);
      }
    }
  }

  /**
   * Sucht eine Componente anhand eines angegebenen Pakets.
   */
  public Component findComponentByPackage(String packageName) {
    Iterator it = packageMap.entrySet().iterator();
    while (it.hasNext()) {
      Entry pair = (Entry) it.next();
      if (packageName.startsWith((String) pair.getKey())) {
        return (Component) pair.getValue();
      }
    }
    return null;
  }

  public ComponentTree addComponent(ComponentNode node) {
    this.componentList.add(node);
    return this;
  }

  public Architecture getArchitecture() {
    return architecture;
  }

  public void setArchitecture(Architecture architecture) {
    this.architecture = architecture;
  }

  public ArrayList<ComponentNode> getComponentList() {
    return componentList;
  }

  public void setComponentList(ArrayList<ComponentNode> componentList) {
    this.componentList = componentList;
  }

  public Map<String, Component> getPackageMap() {
    return packageMap;
  }

  public void setPackageMap(Map<String, Component> packageMap) {
    this.packageMap = packageMap;
  }
}
