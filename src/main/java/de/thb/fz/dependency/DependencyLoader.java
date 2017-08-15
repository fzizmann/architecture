package de.thb.fz.dependency;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Map;
import org.objectweb.asm.ClassReader;

public class DependencyLoader {

  /**
   * Erstellt eine Liste von Klassen anhand eines Paketnamens.
   *
   * @param packageName Paketname
   * @return Liste von Klassen
   */
  public ArrayList<String> generateClassList(String packageName) {
    ArrayList<String> classList = new ArrayList<>();
    for (File dir : this.findDirectoriesByPackageName(packageName)) {
      classList.addAll(this.generateClassList(dir));
    }
    return classList;
  }


  /**
   * Gibt eine Liste aller Ordner zurück die anhand eines Pakets gefunden werden können
   */
  private ArrayList<File> findDirectoriesByPackageName(String packageName) {
    ArrayList<File> dirs = new ArrayList<>();
    try {
      Enumeration<URL> resources = this.getClass().getClassLoader()
          .getResources(packageName.replace('.', '/'));
      while (resources.hasMoreElements()) {
        URL resource = resources.nextElement();
        dirs.add(new File(resource.getFile()));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return dirs;
  }

  /**
   * Erstellt eine Liste der Klassen aus einem gegebenen Ordner.
   *
   * @param dir Datei zur Analyse
   * @return Liste der Klassen eines Ordners
   */
  public ArrayList<String> generateClassList(File dir) {
    ArrayList<String> pathList = new ArrayList<String>();
    File[] files = dir.listFiles();
    if (files != null) {
      for (File file : files) {
        if (file.isDirectory()) {
          pathList.addAll(this.generateClassList(file));
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
  public ArrayList<String> findDependenciesForClass(String cls) throws IOException {
    DependencyVisitor classVisitor = new DependencyVisitor();
    new ClassReader(cls).accept(classVisitor, 0);
    Map<String, Integer> stringIntegerMap = classVisitor.getGlobals()
        .get(cls.replace('.', '/'));
    return new ArrayList<String>(stringIntegerMap.keySet());
  }
}
