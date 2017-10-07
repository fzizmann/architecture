package de.thb.fz.style.pattern;

import de.thb.fz.dsl.Architecture;
import de.thb.fz.style.rule.UsesRule;
import de.thb.fz.violation.Violation;
import java.util.ArrayList;

public class Pipes implements Style {

  public static final String PIPE = "pipe";
  public static final String FILTER = "filter";

  public ArrayList<Violation> validate(Architecture architecture) {
    ArrayList<Violation> violations = new ArrayList<>();
    violations.addAll(new UsesRule(Pipes.PIPE, Pipes.PIPE).execute(architecture));
    violations.addAll(new UsesRule(Pipes.FILTER, Pipes.FILTER).execute(architecture));
    return violations;
  }

}

