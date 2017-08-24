package de.thb.fz.output;

import static de.thb.fz.dsl.Architecture.architecture;
import static de.thb.fz.dsl.Component.component;

import de.thb.fz.analyzer.ComponentIndex;
import de.thb.fz.analyzer.builder.ComponentBuilder;
import de.thb.fz.analyzer.builder.ComponentIndexBuilder;
import de.thb.fz.dependency.DependencyLoader;
import de.thb.fz.dsl.Architecture;
import org.junit.Before;
import org.junit.Test;

public class GraphBuilderTest {

  private Architecture architecture;

  @Before
  public void setUp() throws Exception {
    this.architecture = architecture(
        component("extensions")
            .structure(
                "de.thb.fz"
            )
    );
    ComponentIndex componentIndex = new ComponentIndexBuilder(new DependencyLoader())
        .buildComponentIndex(architecture);
    //schauen was man hier noch machen kann
    new ComponentBuilder(new DependencyLoader(),
        componentIndex).expandComponents();

  }

  @Test
  public void testGraphBuilder() {
//    System.out.println(GraphBuilder.drawGraph(architecture));
  }
}