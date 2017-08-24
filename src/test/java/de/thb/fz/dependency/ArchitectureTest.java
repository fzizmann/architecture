package de.thb.fz.dependency;

import static de.thb.fz.dsl.Architecture.architecture;
import static de.thb.fz.dsl.Component.component;

import de.thb.fz.analyzer.ComponentIndex;
import de.thb.fz.analyzer.builder.ComponentBuilder;
import de.thb.fz.analyzer.builder.ComponentIndexBuilder;
import de.thb.fz.dsl.Architecture;
import de.thb.fz.dsl.Component;
import de.thb.fz.dsl.type.ComponentType;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import junit.extensions.ActiveTestSuite;
import org.junit.Before;
import org.junit.Test;


public class ArchitectureTest {

  private Architecture architecture;

  @Test
  public void connectionTest() {
    architecture.getComponents()
        .forEach(component -> component.getImplementations().forEach(aClass -> {
          //wenn Implementations Klasse in dieser Komponente vorhanden ist.
          if (component.getConnection().containsKey(aClass)) {
            component.getConnection().get(aClass).forEach(usedClass -> {
              if (component.getUsed().get(usedClass).getInterfaces().contains(usedClass)) {
                System.out.println("Erfolg");
              }
            });
          }
        }));
  }

  @Test
  public void printWeights() {
    architecture.getComponents().forEach(component -> {
      //algorithm to find weights
      HashMap<Component, Integer> targetUsageCount = new HashMap<>();
      for (Entry<Class, Component> entry : component.getUsed().entrySet()) {
        targetUsageCount.putIfAbsent(entry.getValue(), 0);
        targetUsageCount.computeIfPresent(entry.getValue(),
            (key, val) -> ++val);
      }
      HashMap<Component, Integer> sourceUsageCount = new HashMap<>();
      for (Entry<Class, HashSet<Component>> entry : component.getUses().entrySet()) {
        entry.getValue().forEach(multiComponent -> {
          sourceUsageCount.putIfAbsent(multiComponent, 0);
          sourceUsageCount.computeIfPresent(multiComponent,
              (key, val) -> ++val);
        });
      }

      //print weights
      targetUsageCount.forEach((targetComponent, integer) -> System.out.println(
          component.getComponentName() + " -> " +
              sourceUsageCount.get(targetComponent) + " -> " +
              targetUsageCount.get(targetComponent) + " -> " +
              targetComponent
      ));
    });
  }

  @Test
  public void printConnections() {
    for (Component sourceComponent : architecture.getComponents()) {
      for (Entry<Class, HashSet<Class>> entry : sourceComponent.getConnection().entrySet()) {
        System.out.println(
            sourceComponent.getComponentName() + " - " +
                entry.getKey() + " -> " +
                entry.getValue().toString()
        );
      }
    }
  }

  @Before
  public void setUp() throws Exception {
    this.architecture = architecture(
        component("extensions")
            .structure(
                "junit.extensions"
            ).implementations(ActiveTestSuite.class)
        , component("framework")
            .structure(
                "junit.framework"
            )
            .interfaces(junit.framework.Test.class)
            .type(ComponentType.MODEL)
        , component("runner")
            .structure(
                "junit.runner"
            )
        , component("textui")
            .structure(
                "junit.textui"
            )
        , component("org")
            .structure(
                "org.junit"
            )
        , component("experimental")
            .structure(
                "org.junit.experimental"
            )
        , component("internal")
            .structure(
                "org.junit.internal"
            )
        , component("matchers")
            .structure(
                "org.junit.matchers"
            )
        , component("rules")
            .structure(
                "org.junit.rules"
            )
        , component("orgRunner")
            .structure(
                "org.junit.runner"
            )
        , component("runners")
            .structure(
                "org.junit.runners"
            )
        , component("validator")
            .structure(
                "org.junit.validator"
            )
        , component("orgRunnerManipulation")
            .structure(
                "org.junit.runner.manipulation"
            )
        , component("orgRunnerNotification")
            .structure(
                "org.junit.runner.notification"
            )
        , component("runnersModel")
            .structure(
                "org.junit.runners.model"
            )
        , component("runnersParameterized")
            .structure(
                "org.junit.runners.parameterized"
            )
        , component("internalBuilders")
            .structure(
                "org.junit.internal.builders"
            )
        , component("internalsMatchers")
            .structure(
                "org.junit.internal.matchers"
            )
        , component("internalRequests")
            .structure(
                "org.junit.internal.requests"
            )
        , component("internalRunners")
            .structure(
                "org.junit.internal.runners"
            )
    );
    ComponentIndex componentIndex = new ComponentIndexBuilder(new DependencyLoader())
        .buildComponentIndex(architecture);

    //schauen was man hier noch machen kann
    new ComponentBuilder(new DependencyLoader(),
        componentIndex).expandComponents();
  }

}