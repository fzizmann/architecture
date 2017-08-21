package de.thb.fz.dependency;

import org.junit.Test;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;


public class DependencyLoaderTest {

  @Test
  public void reflectiveClassList() throws Exception {
    new Reflections("junit", new SubTypesScanner(false))
        .getSubTypesOf(Object.class).forEach(System.out::println);
  }
}