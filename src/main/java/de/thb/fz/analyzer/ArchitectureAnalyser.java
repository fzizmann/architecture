package de.thb.fz.analyzer;

import de.thb.fz.dsl.Architecture;
import de.thb.fz.dsl.Component;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

public class ArchitectureAnalyser {

  public void analyzeInterfaceAndImplementation(Architecture architecture) {
    architecture.getComponents().forEach(
        component -> component.getImplementations().forEach(aClass -> {
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

  public void analyzeConnection(Architecture architecture) {
    architecture.getComponents().forEach(
        sourceComponent -> sourceComponent.getConnection()
            .forEach((key, value) ->
                //TODO speichern in Datenstruktur
                System.out.println(
                    sourceComponent.getComponentName() + " - " +
                        key + " -> " +
                        value.toString()
                )));
  }


  public void analyzeStyle(Architecture architecture) {
    //TODO Architekturstile umbauen, Regeln und Beziehungen als DSL schreiben
    architecture.getStyles().forEach(style -> style.validate(architecture).forEach(
        styleViolation -> System.out
            .println(styleViolation.getViolationMessage() + System.lineSeparator())
    ));
  }

  public void analyzeWeights(Architecture architecture) {
    architecture.getComponentIndex().values().stream().distinct().forEach(component -> {
      HashMap<Component, Integer> targetUsageCount = calculateSourceWeight(component);
      HashMap<Component, Integer> sourceUsageCount = calculateTargetWeight(component);

      //TODO save weights
      targetUsageCount.forEach((targetComponent, integer) -> System.out.println(
          component.getComponentName() + " -> " +
              sourceUsageCount.get(targetComponent) + " -> " +
              targetUsageCount.get(targetComponent) + " -> " +
              targetComponent
      ));
    });
  }

  private HashMap<Component, Integer> calculateTargetWeight(Component component) {
    HashMap<Component, Integer> sourceUsageCount = new HashMap<>();
    for (Entry<Class, HashSet<Component>> entry : component.getUses().entrySet()) {
      entry.getValue().forEach(multiComponent -> {
        sourceUsageCount.putIfAbsent(multiComponent, 0);
        sourceUsageCount.computeIfPresent(multiComponent,
            (key, val) -> ++val);
      });
    }
    return sourceUsageCount;
  }

  private HashMap<Component, Integer> calculateSourceWeight(Component component) {
    HashMap<Component, Integer> targetUsageCount = new HashMap<>();
    for (Entry<Class, Component> entry : component.getUsed().entrySet()) {
      targetUsageCount.putIfAbsent(entry.getValue(), 0);
      targetUsageCount.computeIfPresent(entry.getValue(),
          (key, val) -> ++val);
    }
    return targetUsageCount;
  }


}
