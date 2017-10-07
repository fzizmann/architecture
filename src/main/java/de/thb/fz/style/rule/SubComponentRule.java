package de.thb.fz.style.rule;

import de.thb.fz.dsl.Architecture;
import de.thb.fz.violation.Violation;
import java.util.ArrayList;

public class SubComponentRule implements Rule {

  private String typeA;
  private String typeB;

  public SubComponentRule(String typeA, String typeB) {
    this.typeA = typeA;
    this.typeB = typeB;
  }

  public static Rule subComponentOf(String superComponentType, String subComponentType) {
    return new SubComponentRule(superComponentType, subComponentType);

  }

  @Override
  public ArrayList<Violation> execute(Architecture architecture) {
    ArrayList<Violation> result = new ArrayList<>();
    architecture.getComponentIndex().forEach(
        (aClass, superComponent) -> superComponent.getSubComponents().forEach(subComponent -> {
          if (!superComponent.getType().equals(typeA) && subComponent.getType().equals(typeB)) {
            result.add(new RuleViolation(
                "Subkomponente " + subComponent.getComponentName() +
                    " mit Typ " + superComponent.getType() + " hat unerlaubt eine " +
                    "Überkomponente " + superComponent.getComponentName() +
                    " mit Typ " + superComponent.getType()
            ));
          }
        }));

    return result;
  }
}
