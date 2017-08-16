package de.thb.fz.analyzer.builder;

import de.thb.fz.analyzer.ComponentIndex;
import de.thb.fz.analyzer.ComponentNode;
import de.thb.fz.analyzer.ComponentRoot;
import de.thb.fz.dependency.DependencyLoader;
import de.thb.fz.dsl.Architecture;
import de.thb.fz.dsl.Component;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;

/**
 * Diese Klasse erstellt den Komponentenbaum mittels der Architektur.
 */
public class ComponentRootBuilder {

  private DependencyLoader dependencyLoader;
  private ComponentIndex componentIndex;

  public ComponentRootBuilder(DependencyLoader dependencyLoader,
      ComponentIndex componentIndex) {
    this.dependencyLoader = dependencyLoader;
    this.componentIndex = componentIndex;
  }

  public ComponentRoot buildComponentRoot(Architecture architecture, String basePath) {
    ComponentRoot root = new ComponentRoot(architecture);
    Iterator it = componentIndex.getComponentMap().entrySet().iterator();

    while (it.hasNext()) {
      Entry pair = (Entry) it.next();

      ComponentNode node = new ComponentNode();
      node.setComponent((Component) pair.getValue());
      String pathname = basePath + pair.getKey();
      generateClassListByComponent(node, pathname, basePath);
      root.addComponent(node);
    }
    return root;
  }

  private void generateClassListByComponent(ComponentNode node, String pathname, String basePath) {
    //laden der Klassen in einem Package
    ArrayList<String> classList = dependencyLoader
        .generateClassList(new File(pathname.replace(".", "\\")));
    for (String jClass : classList) {
      findComponentByClassString(basePath, node, jClass);
    }
  }

  private void findComponentByClassString(String basePath, ComponentNode node, String jClass) {
    ArrayList<String> depList = new ArrayList<String>();
    try {
      //laden der Abhängigkeiten
      String shortclass = jClass.replace(basePath, "").replace("\\", ".").replace(".class", "");
      depList.addAll(dependencyLoader.findDependenciesForClass(shortclass));
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
   * Sucht eine Componente anhand eines angegebenen Pakets.
   */
  public Component findComponentByPackage(String packageName) {
    Iterator it = componentIndex.getComponentMap().entrySet().iterator();
    while (it.hasNext()) {
      Entry pair = (Entry) it.next();
      if (packageName.startsWith((String) pair.getKey())) {
        return (Component) pair.getValue();
      }
    }
    return null;
  }

}
