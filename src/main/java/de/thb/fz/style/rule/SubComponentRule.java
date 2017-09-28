package de.thb.fz.style.rule;

import de.thb.fz.dsl.Architecture;
import de.thb.fz.violation.Violation;
import java.util.ArrayList;

public class SubComponentRule implements Rule {

  public Rule subcomponent(String typeA, String typeB) {

    return this;
  }

  public static Rule subComponentOf() {
    return new SubComponentRule();

  }

  @Override
  public ArrayList<Violation> execute(Architecture architecture) {
    return null;
  }
}
