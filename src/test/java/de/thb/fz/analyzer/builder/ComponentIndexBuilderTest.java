package de.thb.fz.analyzer.builder;

import static de.thb.fz.dsl.Architecture.architecture;
import static de.thb.fz.dsl.Component.component;

import de.thb.fz.analyzer.ComponentIndex;
import de.thb.fz.dependency.DependencyLoader;
import de.thb.fz.dsl.Architecture;
import org.junit.Before;
import org.junit.Test;

public class ComponentIndexBuilderTest {

  private Architecture architecture;

  @Before
  public void setUp() throws Exception {
    this.architecture = architecture(
        component("extensions")
            .structure(
                "de.thb.fz"
            )
    );

  }

  @Test
  public void testBuildComponentIndex() {
    ComponentIndexBuilder builder = new ComponentIndexBuilder(new DependencyLoader());
    ComponentIndex index = builder.buildComponentIndex(this.architecture);
    index.getComponentMap().forEach(
        (aClass, component) -> System.out.println(aClass + " -> " + component.getComponentName()));
  }

}