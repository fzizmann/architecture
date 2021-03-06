package de.thb.fz.analyzer;

import de.thb.fz.dsl.Architecture;
import de.thb.fz.dsl.Component;
import de.thb.fz.violation.ArchitectureViolation;
import de.thb.fz.violation.InterfaceViolation;
import de.thb.fz.violation.UndefiendClassViolation;
import de.thb.fz.violation.UnusedInterfaceViolation;
import de.thb.fz.violation.Violation;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import org.codehaus.plexus.util.FileUtils;

public class ArchitectureAnalyser {

  /**
   * Prüft ob Implementations und Interfaces korrekt definiert sind.
   */
  public ArrayList<ArchitectureViolation> analyzeInterfaceAndImplementation(
      Architecture architecture) {
    final ArrayList<ArchitectureViolation> result = new ArrayList<>();
    architecture.getComponents().forEach(
        component -> {
          HashMap<Class, Component> componentViolationList = new HashMap<>(
              component.getUsed());
          component.getInterfaces().forEach(interfaceClass -> {
            if (component.getUsed().containsKey(interfaceClass)) {
              component.getUsed().get(interfaceClass).getImplementations()
                  .forEach(implementation -> {
                    if (interfaceClass.isAssignableFrom(implementation)) {
                      componentViolationList.remove(interfaceClass);
                    }
                  });
            }
          });
          componentViolationList.forEach((targetClass, targetComponent) ->
              component.getConnection().forEach((sourceClass, connectionList) -> {
                if (connectionList.contains(targetClass)) {
                  result.add(
                      new ArchitectureViolation(
                          sourceClass.getName(),
                          targetClass.getName(),
                          component.getComponentName(),
                          targetComponent.getComponentName()));
                }
              }));
        });

    return result;
  }

  /**
   * Erstellt eine Liste definierter aber nicht genutzter Interfaces
   */
  public ArrayList<UnusedInterfaceViolation> analyzeUnusedInterfaces(
      Architecture architecture) {
    ArrayList<UnusedInterfaceViolation> result = new ArrayList<>();
    HashSet<Class> interfaces = new HashSet<>();
    //zusammenfassen aller Interfaces
    architecture.getComponentIndex()
        .forEach((aClass, component) -> interfaces.addAll(component.getInterfaces()));

    //entfernen der tatsächlich genutzten interfaces
    architecture.getComponentIndex().forEach(
        (aClass, component) -> component.getConnection().forEach((cClass, list) -> {
          list.forEach(dclass -> {
            if (interfaces.contains(dclass)) {
              interfaces.remove(dclass);
            }
          });
        }));

    interfaces.forEach(aClass -> result.add(new UnusedInterfaceViolation(aClass.getName())));

    return result;
  }

  /**
   * Erstellt eine Liste von Klassen die im Projekt enthalten aber nicht in der Architektur
   * definiert sind.
   */
  public ArrayList<UndefiendClassViolation> analyzeUndefinedClasses(
      Architecture architecture, ArrayList<Class> projectClasses) {
    architecture.getComponentIndex().forEach((aClass, component) -> {
      if (projectClasses.contains(aClass)) {
        projectClasses.remove(aClass);
      }
    });

    ArrayList<UndefiendClassViolation> undefiendClassViolations = new ArrayList<>();
    projectClasses.forEach(
        aClass -> undefiendClassViolations.add(new UndefiendClassViolation(aClass.getName())));
    return undefiendClassViolations;
  }

  /**
   * Prüft ob alle als Interface definierten Klassen Java-Interfaces sind.
   */
  public ArrayList<Violation> checkInterfaces(Architecture architecture) {
    ArrayList<Violation> result = new ArrayList<>();
    architecture.getComponentIndex().values().stream().distinct()
        .forEach(component -> component.getInterfaces().forEach(
            interfaceClass -> {
              if (!interfaceClass.isInterface()) {
                result.add(new InterfaceViolation(interfaceClass.getName()));
              }
            })
        );
    return result;
  }

  /**
   * Prüft alle definierten Regeln.
   */
  public ArrayList<Violation> analyzeRules(Architecture architecture) {
    ArrayList<Violation> result = new ArrayList<>();
    architecture.getRules().forEach(rule -> rule.execute(architecture).forEach(violation -> {
      boolean resultNotContainsViolation = true;
      for (Violation resultViolation : result) {
        if (resultViolation.getViolationMessage().equals(violation.getViolationMessage())) {
          resultNotContainsViolation = false;
        }
      }
      if (resultNotContainsViolation) {
        result.add(violation);
      }
    }));
    return result;
  }

  /**
   * Prüft ob die definierten Architekturstile eingehalten wurden.
   */
  public ArrayList<Violation> analyzeStyle(Architecture architecture) {
    final ArrayList<Violation> result = new ArrayList<>();
    architecture.getPatterns().forEach(style -> style.validate(architecture).forEach(violation -> {
      boolean resultNotContainsViolation = true;
      for (Violation resultViolation : result) {
        if (resultViolation.getViolationMessage().equals(violation.getViolationMessage())) {
          resultNotContainsViolation = false;
        }
      }
      if (resultNotContainsViolation) {
        result.add(violation);
      }
    }));
    return result;
  }

  /**
   * Errechnet die Gewichte zwischen den Kompoenten. Diese Methode ist für die Graphenzeichnung
   * notwendig.
   */
  public String analyzeWeights(Architecture architecture) {
    final StringBuffer result = new StringBuffer("digraph architecture {")
        .append(System.getProperty("line.separator"));
    architecture.getComponentIndex().values().stream().distinct().forEach(component -> {
      HashMap<Component, Integer> targetUsageCount = calculateSourceWeight(component);
      HashMap<Component, Integer> sourceUsageCount = calculateTargetWeight(component);

      targetUsageCount.forEach((targetComponent, integer) -> result
          .append(component.getComponentName())
          .append(" -> ").append(targetComponent)
          .append(" [ headlabel = \"   ")
          .append(sourceUsageCount.get(targetComponent))
          .append("\", taillabel = \"")
          .append(targetUsageCount.get(targetComponent))
          .append("   \" ];")
          .append(System.getProperty("line.separator")));
    });
    result.append("}");

    try {
      FileUtils.fileWrite("architectureGraph.dot", result.toString());
    } catch (IOException e) {
      e.printStackTrace();
    }

    return result.toString();
  }

  /**
   * Untermethode von analyzeWeights, errechnet das Zielgewicht einer Komponente. Dabei wird die
   * Anzahl der genutzten Komponenten der übergebenen Komponente gezählt.
   */
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

  /**
   * Untermethode von analyzeWeights, errechnet das Startgewicht einer Komponente. Es wird gezählt
   * wieviele Klassen der übergebenen Komponente eine Zielkomponente nutzen.
   */
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
