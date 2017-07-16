package de.thb.fz.dependency;

import static de.thb.fz.dsl.Architecture.architecture;
import static de.thb.fz.dsl.Component.component;
import static de.thb.fz.dsl.JavaPackage.javaPackage;

import de.thb.fz.analyzer.ComponentNode;
import de.thb.fz.analyzer.ComponentTree;
import de.thb.fz.dsl.Architecture;
import de.thb.fz.dsl.Component;
import de.thb.fz.dsl.JavaPackage;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class ArchitectureTest {

  private Architecture architecture;

  @Before
  public void setUp() throws Exception {
    this.architecture = architecture(
        component("extensions")
            .structure(
                javaPackage("junit.extensions")
            ),
        component("framework")
            .structure(
                javaPackage("junit.framework")
            ),
        component("runner")
            .structure(
                javaPackage("junit.runner")
            ),
        component("textui")
            .structure(
                javaPackage("junit.textui")
            ),
        component("org")
            .structure(
                javaPackage("org.junit")
            )
    );
  }

  @Test
  public void testLoader() {
    DependencyLoader loader = new DependencyLoader();
    ComponentTree tree = new ComponentTree();
    String basePath = "C:\\Users\\Friedrich\\Desktop\\junit\\";

    Iterator it = this.architecture.getPackageMap().entrySet().iterator();
    while (it.hasNext()) {
      Entry pair = (Entry) it.next();
      ComponentNode node = new ComponentNode();
      node.setComponentname(((Component) pair.getValue()).getComponentName());
      String pathname = basePath + (String) pair.getKey();

      ArrayList<String> classList = loader
          .generateClassList(new File(pathname.replace(".", "\\")));
      ArrayList<String> depList = new ArrayList<String>();
      for (String jClass : classList) {
        try {
          String shortclass = jClass.replace(basePath, "").replace("\\", ".").replace(".class", "");
          depList.addAll(loader.findDependenciesForClass(shortclass));
          for (String dep : depList) {
            Component cp = architecture.findComponentByPackage(dep);
            if (cp != null) {
              node.addComponent(cp.getComponentName());
            }
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
      tree.addComponent(node);
//      it.remove();
    }

    for (ComponentNode componentNode : tree.getComponentList()) {
      System.out.println("Name " + componentNode.getComponentname());
      for (String conn : componentNode.getConnection()) {
        System.out.println("-> " + conn);
      }
    }
  }

  @Test
  public void testFindComponentsByPackage() {
    Architecture arch = architecture(
        component("TestComponent")
            .structure(
                new JavaPackage("de.thb")
            )
    );
    Component comp = arch.findComponentByPackage("de.thb.fz.test");
    Assert.assertEquals("", comp.getComponentName(), "TestComponent");
  }

//  private void validateDependencies(
//      Component component,
//      Set<String> dependencies,
//      Set<String> allowedDependencies
//  ) {
////    for (String dependency : dependencies) {
////      if (!allowedDependencies.contains(dependency)) {
////        this.printViolation(
////            new Violation(
////                component.getComponentName(),
////                dependency));
////      }
////    }
//  }
//
//  /**
//   * load classes with byte code analysisd
//   */
//  private Set<String> loadDependencies(Set<Class<?>> consistClasses) {
//    Set<String> dList = new HashSet<String>();
//    Map<String, Integer> dependencies = new HashMap<String, Integer>();
//    DependencyTracker tracker = new DependencyTracker();
//    for (Class clazz : consistClasses) {
//      try {
//        dependencies = tracker.loadDependencies(clazz);
//        dList.addAll(dependencies.keySet());
//      } catch (IOException e) {
//        e.printStackTrace();
//      }
//    }
//
//    return dList;
//  }
//
//  /**
//   * load all classes in package
//   */
//  private Set loadPackageClasses(ArrayList<Package> packageArrayList) {
//
//    List<ClassLoader> classLoadersList = new LinkedList<ClassLoader>();
//    classLoadersList.add(ClasspathHelper.contextClassLoader());
//    classLoadersList.add(ClasspathHelper.staticClassLoader());
//    Set<Class<? extends Object>> subTypes = new HashSet<Class<? extends Object>>();
//
//    for (Package pack : packageArrayList) {
//
//      Reflections reflections = new Reflections(new ConfigurationBuilder()
//          .setScanners(new SubTypesScanner(false /* don't exclude Object.class */),
//              new ResourcesScanner())
//          .setUrls(ClasspathHelper.forClassLoader(classLoadersList.toArray(new ClassLoader[0])))
//          .filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix(pack.getName()))));
//
//      subTypes.addAll(reflections.getSubTypesOf(Object.class));
//    }
//    return subTypes;
//  }
//
//  /**
//   * print violations
//   */
//  private void printViolation(Violation violation) {
//    System.out.println(
//        "Komponente: " + violation.getComponent() +
//            " Benutzt: " + violation.getViolatedClass()
//    );
//  }
}