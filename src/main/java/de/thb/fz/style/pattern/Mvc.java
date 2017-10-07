package de.thb.fz.style.pattern;

import de.thb.fz.dsl.Architecture;
import de.thb.fz.style.rule.UsesRule;
import de.thb.fz.violation.Violation;
import java.util.ArrayList;

public class Mvc implements Style {

  public static final String VIEW = "view";
  public static final String MODEL = "model";
  public static final String CONTROLLER = "controller";

  public ArrayList<Violation> validate(Architecture architecture) {
    ArrayList<Violation> violations = new ArrayList<>();
    violations.addAll(new UsesRule(Mvc.VIEW, Mvc.MODEL).execute(architecture));
    violations.addAll(new UsesRule(Mvc.VIEW, Mvc.CONTROLLER).execute(architecture));
    violations.addAll(new UsesRule(Mvc.MODEL, Mvc.CONTROLLER).execute(architecture));

    return violations;
  }

}

