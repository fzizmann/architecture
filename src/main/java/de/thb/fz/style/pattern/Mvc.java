package de.thb.fz.style.pattern;

import de.thb.fz.dsl.Architecture;
import java.util.ArrayList;

public class Mvc implements Style {

  public static final String VIEW = "view";
  public static final String MODEL = "model";
  public static final String CONTROLLER = "controller";

  public ArrayList<PatternViolation> validate(Architecture architecture) {
    ArrayList<PatternViolation> violations = new ArrayList<>();
    architecture.getComponentIndex().values().stream().distinct().forEach(
        component -> component.getUsed().values().stream().distinct().forEach(usedComponent -> {
          //Verletzung liegt vor wenn Model auf Controller zugreift oder VIEW auf etwas anderes als View
          if (component.getType() != null && usedComponent.getType() != null) {
            if ((component.getType().equals(MODEL) && usedComponent.getType().equals(CONTROLLER)) ||
                (component.getType().equals(VIEW) && !usedComponent.getType().equals(VIEW))) {
              violations.add(
                  new PatternViolation(
                      component.getComponentName() + " mit Typ " + component.getType()
                          + " greift auf "
                          + usedComponent.getComponentName() + " mit Typ " + usedComponent.getType()
                          + " zu."));
            }
          }
        }));
    return violations;
  }

}

