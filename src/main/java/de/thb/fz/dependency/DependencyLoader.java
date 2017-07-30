package de.thb.fz.dependency;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import org.objectweb.asm.ClassReader;

public class DependencyLoader {

  /**
   * Erstellt eine Liste der Klassen aus einem gegebenen Ordner.
   *
   * @param dir Datei zur Analyse
   * @return Liste der
   */
  public static ArrayList<String> generateClassList(File dir) {
    ArrayList<String> pathList = new ArrayList<String>();
    File[] files = dir.listFiles();
    if (files != null) {
      for (File file : files) {
        if (file.isDirectory()) {
          pathList.addAll(DependencyLoader.generateClassList(file));
        } else {
          int strLen = file.getName().length();
          if (file.getName().substring(strLen - 5, strLen).equals("class")) {
            pathList.add(file.getAbsolutePath());
          }
        }
      }
    }

    return pathList;
  }

  /**
   * Lädt alle Äbhangigkeiten einer Klasse.
   */
  public static ArrayList<String> findDependenciesForClass(String clazz) throws IOException {
    DependencyVisitor classVisitor = new DependencyVisitor();
    String className = clazz;
    new ClassReader(className).accept(classVisitor, 0);
    Map<String, Integer> stringIntegerMap = classVisitor.getGlobals()
        .get(className.replace('.', '/'));
    return new ArrayList<String>(stringIntegerMap.keySet());
  }
}
