package de.thb.fz.dependency;

import java.io.File;
import java.util.ArrayList;
import org.junit.Assert;
import org.junit.Test;


public class DependencyLoaderTest {


  @Test
  public void generateClassList() throws Exception {
    ArrayList<String> list = DependencyLoader.generateClassList(
        new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath())
    );
    String testClass = "C:\\Users\\Friedrich\\git\\masterarbeit\\target\\test-classes\\de\\thb\\fz\\dependency\\DependencyLoaderTest.class";
    Assert.assertTrue(list.stream().anyMatch(item -> testClass.equals(item)));
  }

}