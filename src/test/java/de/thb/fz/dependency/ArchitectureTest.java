package de.thb.fz.dependency;

import static de.thb.fz.dsl.Architecture.architecture;
import static de.thb.fz.dsl.Component.component;
import static de.thb.fz.dsl.JavaPackage.javaPackage;

import de.thb.fz.dsl.Architecture;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;


public class ArchitectureTest {

  private Architecture architecture;

  @Before
  public void setUp() throws Exception {
    this.architecture = architecture(
        component("Unit1")
            .structure(
                javaPackage("de.fz.first.*"),
                javaPackage("de.fz.second.*")
            )
            .interfaces(
                String.class,
                Number.class
            )
            .implementations(
                Integer.class,
                Object.class)
        ,
        component("Unit2")
            .structure(
                javaPackage("test.test1.*")
            )
            .interfaces(
                Arrays.class
            )
            .implementations(
                String.class
            )
    );
  }

  @Test
  public void testLoader() {
  }

//  public Architecture defineArchitecture() {
////    Architecture archDes = new Architecture();
////
////    Component comp1 = new Component("View");
////    comp1.getConsist().add(TestClass.class.getPackage());
////    archDes.getComponents().add(comp1);
////
////    Component comp2 = new Component("Model");
////    comp2.getConsist().add(TestClass.class.getPackage());
////    comp2.getConsist().add(Analyzer.class.getPackage());
////    archDes.getComponents().add(comp2);
//
////    return archDes;
//    return new Architecture();
//  }
//
//  public void analyzeArchitecture(Architecture description) {
//    Architecture architectureDescription = description.defineArchitecture();
//    Set<Class<? extends Object>> consistClasses = new HashSet<Class<? extends Object>>();
//    Set<Class<? extends Object>> usedClasses = new HashSet<Class<? extends Object>>();
//    Set<String> dependencies = new HashSet<String>();
//    Set<String> allowedDependencies = new HashSet<String>();
//
//    for (Component component : architectureDescription.getComponents()) {
//      // 1. load all classes in consists packages
////      consistClasses = this.loadPackageClasses(component.getConsist());
//      // 2. load all dependency classes of classes in consists packages
//      dependencies = this.loadDependencies(consistClasses);
////      for (Component usedComponent : component.getUses()) {
////        // 3. load all classes from uses
////        usedClasses = this.loadPackageClasses(usedComponent.getConsist());
////      }
//      // 4. load all dependency classes of classes from uses
//      allowedDependencies.addAll(this.loadDependencies(usedClasses));
//      // 5. compare loaded classes
//      this.validateDependencies(component, dependencies, allowedDependencies);
//    }
//  }
//
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