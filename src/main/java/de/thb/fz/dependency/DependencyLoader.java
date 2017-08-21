package de.thb.fz.dependency;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import org.objectweb.asm.ClassReader;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

public class DependencyLoader {

  /**
   * Erstellt eine Liste von Klassen anhand eines Paketnamens.
   *
   * @param packageName Paketname
   * @return Liste von Klassen
   */
  public Set<Class<?>> generateClassList(String packageName) {
    return new Reflections(packageName, new SubTypesScanner(false))
        .getSubTypesOf(Object.class);
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
