package de.thb.fz.dependency;

import de.app.controller.ControllerA;
import de.app.model.ModelA;
import de.thb.fz.analyzer.ComponentIndex;
import de.thb.fz.analyzer.builder.ComponentBuilder;
import de.thb.fz.analyzer.builder.ComponentIndexBuilder;
import de.thb.fz.dsl.Architecture;
import de.thb.fz.dsl.Component;
import de.thb.fz.style.Mvc;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

import static de.thb.fz.dsl.Architecture.architecture;
import static de.thb.fz.dsl.Component.component;


public class ArchitectureTest {

    private Architecture architecture;
    private ComponentIndex componentIndex;

    @Test
    public void connectionTest() {
        architecture.getComponents()
                .forEach(component -> component.getImplementations().forEach(aClass -> {
                    //wenn Implementations Klasse in dieser Komponente vorhanden ist.
                    if (component.getConnection().containsKey(aClass)) {
                        component.getConnection().get(aClass).forEach(usedClass -> {
                            if (component.getUsed().get(usedClass).getInterfaces().contains(usedClass)) {
                                //diese Zeilen werden ausgeführt, falls eine implementierung ein interface benutzt
                                System.out.println("Erfolg");
                            }
                        });
                    }
                }));
    }

    @Test
    public void componentTest() {
        this.componentIndex.getComponentMap().values().stream().distinct()
                .forEach(component -> System.out.println(component.getComponentName()));
    }

    @Test
    public void styleTest() {
        this.architecture.getStyles().forEach(style -> style.validate(componentIndex).forEach(
                styleViolation -> System.out
                        .println(styleViolation.getViolationMessage() + System.lineSeparator())
        ));
    }

    @Test
    public void printWeights() {
        this.componentIndex.getComponentMap().values().stream().distinct().forEach(component -> {
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
                component("model")
                        .structure(
                                "de.app.model"
                        ).type(Mvc.MODEL)
                        .interfaces(ModelA.class)
                , component("controller")
                        .structure(
                                "de.app.controller"
                        )
                        .type(Mvc.CONTROLLER)
                        .implementations(ControllerA.class)
                , component("view")
                        .structure(
                                "de.app.view"
                        ).type(Mvc.VIEW)
        ).styles(
                new Mvc()
        );

        this.componentIndex = new ComponentIndexBuilder(new DependencyLoader())
                .buildComponentIndex(architecture);

        //schauen was man hier noch machen kann
        new ComponentBuilder(new DependencyLoader(),
                componentIndex).expandComponents();
    }

}