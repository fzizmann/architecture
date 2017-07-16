package de.thb.fz.dependency;

import de.thb.fz.dsl.JavaPackage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import org.objectweb.asm.ClassReader;

public class DependencyLoader {

  /**
   * Erstellt eine Liste der Klassen aus einem gegebenen Ordner.
   *
   * @param dir Datei zur Analyse
   * @return Liste der
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
   * @param filePath zu analysierenden Ordner
   */
  public ArrayList<JavaPackage> findDependancysByFilePath(String filePath) {
    ArrayList<JavaPackage> packageList = new ArrayList<>();
    DependencyVisitor v = new DependencyVisitor();

    try {
      ArrayList<String> fileList = this.generateClassList(new File(filePath));

      //lädt alle Klassen und Abhängigkeiten in einem Ordner
      for (String path : fileList) {
        FileInputStream f = new FileInputStream(path);
        (new ClassReader(f)).accept(v, 0);
        Set<String> classPackages = v.getPackages();

        for (String className : classPackages) {
          JavaPackage jPackage = new JavaPackage(className.getClass().getPackage().toString());
          String[] strclass = className.split(".");
          jPackage.setClassName(strclass[strclass.length - 1]);
          jPackage.setFile(path);
          packageList.add(jPackage);
        }
      }
    } catch (IOException ex) {
      ex.printStackTrace();
    }

    return packageList;
  }

  /**
   * Lädt alle Äbhangigkeiten einer Klasse.
   */
  public ArrayList<String> findDependenciesForClass(String clazz) throws IOException {
    DependencyVisitor classVisitor = new DependencyVisitor();
    String className = clazz;
    new ClassReader(className).accept(classVisitor, 0);
    Map<String, Integer> stringIntegerMap = classVisitor.getGlobals()
        .get(className.replace('.', '/'));
    return new ArrayList<String>(stringIntegerMap.keySet());
  }
}
