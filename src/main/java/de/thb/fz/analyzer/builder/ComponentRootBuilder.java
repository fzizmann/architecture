package de.thb.fz.analyzer.builder;

import de.thb.fz.analyzer.ComponentNode;
import de.thb.fz.analyzer.ComponentRoot;
import de.thb.fz.dependency.DependencyLoader;
import de.thb.fz.dsl.Architecture;
import de.thb.fz.dsl.Component;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Diese Klasse erstellt den Komponentenbaum mittels der Architektur.
 */
// TODO Zugriff auf Klassenmember in Methoden entfernen, lieber Aufrufparameter zu übergeben
public class ComponentRootBuilder {

  private DependencyLoader dependencyLoader;
  private String basePath;
  private Architecture architecture;
  private ComponentRoot tree;

  public ComponentRootBuilder(
      DependencyLoader dependencyLoader,
      String basePath,
      Architecture architecture) {
    this.dependencyLoader = dependencyLoader;
    this.basePath = basePath;
    this.architecture = architecture;
  }

  public ComponentRoot buildComponentTree() {
    this.tree = new ComponentRoot(architecture);
    this.buildPackageComponentMap();
    Iterator it = this.tree.getPackageMap().entrySet().iterator();

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
      tree.addComponent(node);
      node.setConnection(this.minimizeConnectionList(node.getConnection()));
    }
    return tree;
  }

  public ArrayList<String> minimizeConnectionList(ArrayList<String> connection) {
    Set<String> minimizedList = new HashSet<String>();
    minimizedList.addAll(connection);
    return new ArrayList<String>(minimizedList);
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
      for (String jPackage : component.getStructure()) {
        this.tree.getPackageMap().put(jPackage, component);
      }
    }
  }


  /**
   * Sucht eine Componente anhand eines angegebenen Pakets.
   */
  public Component findComponentByPackage(String packageName) {
    Iterator it = this.tree.getPackageMap().entrySet().iterator();
    while (it.hasNext()) {
      Entry pair = (Entry) it.next();
      if (packageName.startsWith((String) pair.getKey())) {
        return (Component) pair.getValue();
      }
    }
    return null;
  }

}
