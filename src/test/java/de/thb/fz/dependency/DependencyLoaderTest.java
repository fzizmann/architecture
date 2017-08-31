package de.thb.fz.dependency;

import java.util.Set;
import java.util.regex.Pattern;
import junit.framework.TestResult;
import org.junit.Test;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;


public class DependencyLoaderTest {

  @Test
  public void reflectiveClassList() throws Exception {
    Reflections reflections = new Reflections("junit.extensions", new ResourcesScanner());
    Set<String> test = reflections.getResources(Pattern.compile(".*"));
    test.forEach(s -> {
      try {
        String replace = s.replace('/', '.').replace(".class", "");
        System.out.println(Class.forName(replace));
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      }
    });
  }

  @Test
  public void loadDepForClass() throws Exception {
    DependencyLoader dependencyLoader = new DependencyLoader();
    System.out.println(TestResult.class.getName());
    dependencyLoader.findDependenciesForClass(TestResult.class.getName()).forEach(
        System.out::println);
  }
}