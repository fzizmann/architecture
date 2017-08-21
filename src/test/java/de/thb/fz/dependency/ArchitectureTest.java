package de.thb.fz.dependency;

import static de.thb.fz.dsl.Architecture.architecture;
import static de.thb.fz.dsl.Component.component;

import de.thb.fz.analyzer.ComponentIndex;
import de.thb.fz.analyzer.builder.ComponentBuilder;
import de.thb.fz.analyzer.builder.ComponentIndexBuilder;
import de.thb.fz.dsl.Architecture;
import de.thb.fz.dsl.Component;
import java.io.IOException;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;


public class ArchitectureTest {

  private Architecture architecture;

  @Before
  public void setUp() throws Exception {
    this.architecture = architecture(
        component("extensions")
            .structure(
                "junit.extensions"
            ),
        component("framework")
            .structure(
                "junit.framework"
            ),
        component("runner")
            .structure(
                "junit.runner"
            ),
        component("textui")
            .structure(
                "junit.textui"
            ),
        component("org")
            .structure(
                "org.junit"
            ),
        component("experimental")
            .structure(
                "org.junit.experimental"
            ),
        component("internal")
            .structure(
                "org.junit.internal"
            ),
        component("matchers")
            .structure(
                "org.junit.matchers"
            ),
        component("rules")
            .structure(
                "org.junit.rules"
            ),
        component("orgRunner")
            .structure(
                "org.junit.runner"
            ),
        component("runners")
            .structure(
                "org.junit.runners"
            ),
        component("validator")
            .structure(
                "org.junit.validator"
            ),
        component("orgRunnerManipulation")
            .structure(
                "org.junit.runner.manipulation"
            ),
        component("orgRunnerNotification")
            .structure(
                "org.junit.runner.notification"
            ),
        component("runnersModel")
            .structure(
                "org.junit.runners.model"
            ),
        component("runnersParameterized")
            .structure(
                "org.junit.runners.parameterized"
            ),
        component("internalBuilders")
            .structure(
                "org.junit.internal.builders"
            ),
        component("internalsMatchers")
            .structure(
                "org.junit.internal.matchers"
            ),
        component("internalRequests")
            .structure(
                "org.junit.internal.requests"
            ),
        component("internalRunners")
            .structure(
                "org.junit.internal.runners"
            )
    );

  }

  @Test
  public void testLoader() throws IOException, ClassNotFoundException {
    ComponentIndex componentIndex = new ComponentIndexBuilder(new DependencyLoader())
        .createComponentIndex(architecture);

    ComponentBuilder componentBuilder = new ComponentBuilder(new DependencyLoader(),
        componentIndex);
    componentBuilder.findComponentClasses(architecture);
    componentBuilder.findComponentUsages(architecture);

//    System.out.println(GraphBuilder.drawGraph(architecture));

    for (Component sourceComponent : architecture.getComponents()) {
      for (Map.Entry<Class, Class> entry : sourceComponent.getConnection().entrySet()) {
        if (sourceComponent.getUsed().get(entry.getKey()) != null) {
          System.out.println(
              sourceComponent.getComponentName() + " - " +
                  entry.getKey() + " -> " +
                  entry.getValue().getName() + " - " +
                  sourceComponent.getUsed().get(entry.getKey()).getComponentName()
          );
        }
      }
    }
  }
}