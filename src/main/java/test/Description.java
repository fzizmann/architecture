package test;

public class Description {

//  public ArchitectureDescription defineArchitecture() {
////    ArchitectureDescription archDes = new ArchitectureDescription();
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
//    return new ArchitectureDescription();
//  }
//
//  public void analyzeArchitecture(Architecture description) {
//    ArchitectureDescription architectureDescription = description.defineArchitecture();
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
