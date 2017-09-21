package de.thb.fz.style.pattern;

import de.thb.fz.dsl.Architecture;
import java.util.ArrayList;

public class Pipes implements Style {

  public static final String PIPE = "pipe";
  public static final String FILTER = "filter";

  public ArrayList<PatternViolation> validate(Architecture architecture) {
    ArrayList<PatternViolation> violations = new ArrayList<>();
    architecture.getComponentIndex().values().stream().distinct().forEach(
        component -> component.getUsed().values().stream().distinct().forEach(usedComponent -> {
          //Verletzung liegt vor wenn Model auf Controller zugreift oder VIEW auf etwas anderes als View
          if (component.getType() != null && usedComponent.getType() != null) {
            if ((component.getType().equals(PIPE) && usedComponent.getType().equals(PIPE)) ||
                (component.getType().equals(FILTER) && usedComponent.getType().equals(FILTER))) {
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

