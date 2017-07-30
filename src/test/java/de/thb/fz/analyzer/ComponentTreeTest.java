package de.thb.fz.analyzer;

import static de.thb.fz.dsl.Architecture.architecture;
import static de.thb.fz.dsl.Component.component;

import de.thb.fz.dsl.Architecture;
import de.thb.fz.dsl.Component;
import de.thb.fz.dsl.JavaPackage;
import org.junit.Assert;
import org.junit.Test;

public class ComponentTreeTest {

  @Test
  public void testFindComponentsByPackage() {

    Architecture arch = architecture(
        component("TestComponent")
            .structure(
                new JavaPackage("de.thb")
            )
    );
    ComponentTree tree = new ComponentTree(arch);
    Component comp = tree.findComponentByPackage("de.thb.fz.test");
    Assert.assertEquals("", comp.getComponentName(), "TestComponent");
  }
}