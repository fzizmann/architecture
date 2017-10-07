package de.thb.fz.analyzer;

import de.thb.fz.dsl.Architecture;
import de.thb.fz.dsl.Component;
import de.thb.fz.style.pattern.PatternViolation;
import de.thb.fz.violation.ArchitectureViolation;
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
        component -> component.getImplementations().forEach(aClass -> {
          HashMap<Class, HashSet<Class>> componentViolationList = new HashMap<>(
              component.getConnection());
          // prüfen ob eine erlaubte Verbindung vorliegt und diese aus der Verletzungsliste entfernen
          if (component.getConnection().containsKey(aClass)) {
            component.getConnection().get(aClass).forEach(usedClass -> {
              if (component.getUsed().get(usedClass).getInterfaces().contains(usedClass)) {
                componentViolationList.remove(aClass);
              }
            });
          }
          // umwandlung der Verletzungsliste zu Architekturverletzungen
          componentViolationList.forEach((sourceClass, classes) -> {
            classes.forEach(targetClass -> {
              result.add(
                  new ArchitectureViolation(sourceClass.getName(), targetClass.getName(),
                      component.getComponentName(),
                      architecture.getComponentIndex().get(targetClass).getComponentName())
              );
            });
          });
        }));

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
        (aClass, component) -> component.getImplementations().forEach(implementationClass -> {
          if (component.getConnection().containsKey(implementationClass)) {
            component.getConnection().get(implementationClass).forEach(usedClass -> {
              if (interfaces.contains(usedClass)) {
                interfaces.remove(usedClass);
              }
            });
          }
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
   * Prüft alle definierten Regeln.
   */
  public ArrayList<Violation> analyzeRules(Architecture architecture) {
    ArrayList<Violation> result = new ArrayList<>();
    architecture.getRules().forEach(rule -> result.addAll(rule.execute(architecture)));
    return result;
  }

  /**
   * Prüft ob die definierten Architekturstile eingehalten wurden.
   */
  public ArrayList<PatternViolation> analyzeStyle(Architecture architecture) {
    final ArrayList<PatternViolation> result = new ArrayList<>();
    //TODO Architekturstile umbauen, Regeln und Beziehungen als DSL schreiben
    architecture.getStyles().forEach(style -> result.addAll(style.validate(architecture)));
    return result;
  }

  /**
   * Errechnet die Gewichte zwischen den Kompoenten. Diese Methode ist für die Graphenzeichnung
   * notwendig.
   */
  public String analyzeWeights(Architecture architecture) {
    final StringBuffer result = new StringBuffer("digraph architecture {");
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
          .append("\" ];"));
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
