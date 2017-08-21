package de.thb.fz.dependency;

import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.junit.Assert;
import org.junit.Test;
import org.reflections.Reflections;


public class DependencyLoaderTest {


  @Test
  public void generateClassList() throws Exception {
    DependencyLoader dependencyLoader = new DependencyLoader();
    ArrayList<String> list = dependencyLoader.generateClassList(
        new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath())
    );
    String testClass = "C:\\Users\\Friedrich\\git\\masterarbeit\\target\\test-classes\\de\\thb\\fz\\dependency\\DependencyLoaderTest.class";
    Assert.assertTrue(list.stream().anyMatch(item -> testClass.equals(item)));
  }

  @Test
  public void reflectiveClassList() throws Exception {
    Reflections reflections = new Reflections("de.thb.fz");

    Set<Class<? extends Object>> allClasses =
        reflections.getSubTypesOf(Object.class);
    System.out.println("test");
  }

  @Test
  public void fast() throws ClassNotFoundException {
    List<String> fast = new FastClasspathScanner("junit.framework")
        .scan()
        .getNamesOfAllClasses();
    for (String s : fast) {
      Class cls = Class.forName(s);
      System.out.println(cls.getName());
    }
  }

}