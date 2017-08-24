package de.thb.fz.dependency;

import java.io.IOException;
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
    Reflections reflections = new Reflections(packageName, new ResourcesScanner());
    Set<String> resources = reflections.getResources(Pattern.compile(".*"));
    resources.forEach(className -> {
      try {
        if (className.endsWith(".class")) {
          result.add(Class.forName(className.replace('/', '.').replace(".class", "")));
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

    DependencyVisitor classVisitor = new DependencyVisitor();
    try {
      new ClassReader(aClass).accept(classVisitor, 0);
    } catch (IOException e) {
      e.printStackTrace();
    }

    ArrayList<Class> classes = new ArrayList<>();
    classVisitor.getGlobals().get(aClass.replace('.', '/')).forEach((s, integer) -> {
      try {
        classes.add(Class.forName(s));
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      }
    });
    return classes;
  }
}
