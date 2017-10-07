package de.thb.fz.style.rule;

import de.thb.fz.dsl.Architecture;
import de.thb.fz.violation.Violation;
import java.util.ArrayList;

public class UsesRule implements Rule {

  private String typeA;
  private String typeB;

  public UsesRule(String typeA, String typeB) {
    this.typeA = typeA;
    this.typeB = typeB;
  }

  public static Rule notAllowed(String typeA, String typeB) {

    return new UsesRule(typeA, typeB);
  }

  @Override
  public ArrayList<Violation> execute(Architecture architecture) {
    ArrayList<Violation> result = new ArrayList<>();
    architecture.getComponentIndex().forEach(
        (componentClass, sourceComponent) -> sourceComponent.getUsed()
            .forEach((aClass, targetComponent) -> {
              if (sourceComponent.getType() != null &&
                  sourceComponent.getType() != null &&
                  sourceComponent.getType().equals(typeA) &&
                  targetComponent.getType().equals(typeB)) {
                result.add(new RuleViolation(
                    "Komponente " + sourceComponent.getComponentName() +
                        " mit Typ " + sourceComponent.getType() + " greift unerlaubt auf " +
                        "Komponente " + targetComponent.getComponentName() +
                        " mit Typ " + sourceComponent.getType()
                ));
              }
            }));

    return result;
  }
}
