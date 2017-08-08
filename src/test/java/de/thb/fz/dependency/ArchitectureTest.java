package de.thb.fz.dependency;

import static de.thb.fz.dsl.Architecture.architecture;
import static de.thb.fz.dsl.Component.component;
import static de.thb.fz.dsl.JavaPackage.javaPackage;

import de.thb.fz.analyzer.ComponentTree;
import de.thb.fz.analyzer.builder.ComponentTreeBuilder;
import de.thb.fz.dsl.Architecture;
import de.thb.fz.output.GraphBuilder;
import org.junit.Before;
import org.junit.Test;


public class ArchitectureTest {

  private Architecture architecture;

  @Before
  public void setUp() throws Exception {
    this.architecture = architecture(
        component("extensions")
            .structure(
                javaPackage("junit.extensions")
            ),
        component("framework")
            .structure(
                javaPackage("junit.framework")
            ),
        component("runner")
            .structure(
                javaPackage("junit.runner")
            ),
        component("textui")
            .structure(
                javaPackage("junit.textui")
            ),
        component("org")
            .structure(
                javaPackage("org.junit")
            ),
        component("experimental")
            .structure(
                javaPackage("org.junit.experimental")
            ),
        component("internal")
            .structure(
                javaPackage("org.junit.internal")
            ),
        component("matchers")
            .structure(
                javaPackage("org.junit.matchers")
            ),
        component("rules")
            .structure(
                javaPackage("org.junit.rules")
            ),
        component("orgRunner")
            .structure(
                javaPackage("org.junit.runner")
            ),
        component("runners")
            .structure(
                javaPackage("org.junit.runners")
            ),
        component("validator")
            .structure(
                javaPackage("org.junit.validator")
            ),
        component("orgRunnerManipulation")
            .structure(
                javaPackage("org.junit.runner.manipulation")
            ),
        component("orgRunnerNotification")
            .structure(
                javaPackage("org.junit.runner.notification")
            ),
        component("runnersModel")
            .structure(
                javaPackage("org.junit.runners.model")
            ),
        component("runnersParameterized")
            .structure(
                javaPackage("org.junit.runners.parameterized")
            ),
        component("internalBuilders")
            .structure(
                javaPackage("org.junit.internal.builders")
            ),
        component("internalsMatchers")
            .structure(
                javaPackage("org.junit.internal.matchers")
            ),
        component("internalRequests")
            .structure(
                javaPackage("org.junit.internal.requests")
            ),
        component("internalRunners")
            .structure(
                javaPackage("org.junit.internal.runners")
            )
    );

  }

  @Test
  public void testLoader() {
    ComponentTreeBuilder treeBuilder = new ComponentTreeBuilder(
        new DependencyLoader(),
        "C:\\Users\\Friedrich\\Desktop\\junit\\",
        this.architecture);
    ComponentTree tree = treeBuilder
        .buildComponentTree();
    System.out.println(GraphBuilder.drawGraph(tree));
  }
}