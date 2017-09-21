package de.thb.fz.style.rule;

import de.thb.fz.dsl.Architecture;
import de.thb.fz.violation.Violation;
import java.util.ArrayList;

public class UsesRule implements Rule {

  public Rule uses(String typeA, String typeB) {

    return this;
  }

  @Override
  public ArrayList<Violation> execute(Architecture architecture) {
    return null;
  }
}
