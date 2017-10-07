package de.thb.fz.dependency;

import java.util.ArrayList;
import java.util.Set;
import junit.extensions.RepeatedTest;
import junit.framework.Protectable;
import junit.framework.TestResult;
import org.junit.Assert;
import org.junit.Test;


public class DependencyLoaderTest {

  @Test
  public void reflectiveClassList() throws Exception {
    DependencyLoader dependencyLoader = new DependencyLoader();
    Set<Class<?>> classes = dependencyLoader.generateClassList("junit.extensions");
    Assert.assertTrue(classes.contains(RepeatedTest.class));
  }

  @Test
  public void loadDepForClass() throws Exception {
    DependencyLoader dependencyLoader = new DependencyLoader();
    ArrayList<Class> dependencyClass = dependencyLoader
        .findDependenciesForClass(TestResult.class.getName());
    Assert.assertTrue(dependencyClass.contains(Protectable.class));
  }
}