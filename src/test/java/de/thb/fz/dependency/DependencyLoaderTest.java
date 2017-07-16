package de.thb.fz.dependency;

import static org.junit.Assert.assertTrue;

import de.thb.fz.dsl.JavaPackage;
import java.io.File;
import java.util.ArrayList;
import org.junit.Test;


public class DependencyLoaderTest {


  @Test
  public void generateClassList() throws Exception {
    DependencyLoader initialisator = new DependencyLoader();
    ArrayList<String> list = initialisator.generateClassList(
        new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath())
    );
    String testClass = "C:\\Users\\Friedrich\\git\\masterarbeit\\target\\test-classes\\de\\thb\\fz\\dependency\\DependencyLoaderTest.class";
    assertTrue(list.stream().anyMatch(item -> testClass.equals(item)));
  }

  @Test
  public void findDependancys() throws Exception {
    DependencyLoader initialisator = new DependencyLoader();
    String path = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
    ArrayList<JavaPackage> depList = initialisator.findDependancysByFilePath(path);
    for (JavaPackage jPackage : depList) {
      System.out.println("PackageName: " + jPackage.getPackageName());
      System.out.println("Class: " + jPackage.getClass());
      System.out.println("ClassName: " + jPackage.getClassName());
      System.out.println("File: " + jPackage.getFile());
    }
  }

}