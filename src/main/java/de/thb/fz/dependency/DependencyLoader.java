package de.thb.fz.dependency;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
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
    resources.forEach(s -> {
      try {
        result.add(Class.forName(s.replace('/', '.').replace(".class", "")));
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      }
    });
    return result;
  }

  /**
   * Lädt alle Äbhangigkeiten einer Klasse.
   */
  public ArrayList<String> findDependenciesForClass(String cls) throws IOException {
    DependencyVisitor classVisitor = new DependencyVisitor();
    new ClassReader(cls).accept(classVisitor, 0);
    Map<String, Integer> stringIntegerMap = classVisitor.getGlobals()
        .get(cls.replace('.', '/'));
    return new ArrayList<>(stringIntegerMap.keySet());
  }
}
