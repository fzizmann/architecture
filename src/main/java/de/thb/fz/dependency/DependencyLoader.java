package de.thb.fz.dependency;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;
import org.objectweb.asm.ClassReader;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;

public class DependencyLoader {

  /**
   * Erstellt eine Liste von Klassen anhand eines Paketnamens.
   *
   * @param packageName Paketname
   * @return Liste von Klassen
   */
  public Set<Class<?>> generateClassList(String packageName) {
    Set<Class<?>> result = new HashSet<>();
    Reflections reflections = new Reflections(packageName + ".", new ResourcesScanner());
    ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
    Set<String> resources = reflections.getResources(Pattern.compile(".*"));
    resources.forEach(className -> {
      try {
        if (className.endsWith(".class")) {
          result
              .add(contextClassLoader.loadClass(className.replace('/', '.').replace(".class", "")));
        }
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      }
    });
    return result;
  }

  /**
   * Lädt alle Äbhangigkeiten einer Klasse.
   */
  public ArrayList<Class> findDependenciesForClass(String aClass) {

    DependencyList dependencyList = new DependencyList();
    DependencyVisitor classVisitor = new DependencyVisitor(dependencyList);
    ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
    try {
      InputStream stream = contextClassLoader
          .getResourceAsStream(aClass.replace('.', '/') + ".class");
      ClassReader classReader = new ClassReader(stream);
      classReader.accept(classVisitor, 0);
    } catch (IOException e) {
      e.printStackTrace();
    }

    ArrayList<Class> classes = new ArrayList<>();
    dependencyList.getDependencies().forEach((s) -> {
      try {
        classes.add(contextClassLoader.loadClass(s));
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      }
    });
    return classes;
  }
}
