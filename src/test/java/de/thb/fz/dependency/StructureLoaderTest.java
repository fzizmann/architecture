package de.thb.fz.dependency;

import static de.fz.dsl.ArchitectureDescription.architecture;
import static de.fz.dsl.Component.component;
import static de.fz.dsl.JavaPackage.javaPackage;

import de.fz.analyzer.Analyzer;
import de.fz.dsl.ArchitectureDescription;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import test.EntryPoint;


public class StructureLoaderTest {

  @Before
  public void setUp() throws Exception {
  }

  @Test
  public void testLoader() {
    ArchitectureDescription arch = architecture(
        component("Unit1")
            .structure(
                javaPackage("de.fz.first.*"),
                javaPackage("de.fz.second.*")
            )
            .interfaces(
                EntryPoint.class,
                Analyzer.class
            )
            .implementations(
                EntryPoint.class,
                Analyzer.class)
        ,
        component("Unit2")
            .structure(
                javaPackage("test.test1.*")
            )
            .interfaces(
                Arrays.class
            )
            .implementations(
                String.class
            )
    );
  }
}