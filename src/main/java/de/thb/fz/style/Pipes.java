package de.thb.fz.style;

import de.thb.fz.analyzer.ComponentIndex;
import java.util.ArrayList;

public class Pipes implements Style {

  public static final String PIPE = "pipe";
  public static final String FILTER = "filter";

  public ArrayList<StyleViolation> validate(ComponentIndex componentIndex) {
    ArrayList<StyleViolation> violations = new ArrayList<>();
    componentIndex.getComponentMap().values().stream().distinct().forEach(
        component -> component.getUsed().values().stream().distinct().forEach(usedComponent -> {
          //Verletzung liegt vor wenn Model auf Controller zugreift oder VIEW auf etwas anderes als View
          if (component.getType() != null && usedComponent.getType() != null) {
            if ((component.getType().equals(PIPE) && usedComponent.getType().equals(PIPE)) ||
                (component.getType().equals(FILTER) && usedComponent.getType().equals(FILTER))) {
              violations.add(
                  new StyleViolation(
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

