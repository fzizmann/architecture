package de.thb.fz.style.rule;

import de.thb.fz.dsl.Architecture;
import de.thb.fz.violation.Violation;
import java.util.ArrayList;

public class SubComponentRule implements Rule {

  public Rule subcomponent(String typeA, String typeB) {

    return this;
  }

  public static Rule subComponentOf(String superComponentType, String subComponentType) {
    return new SubComponentRule();

  }

  @Override
  public ArrayList<Violation> execute(Architecture architecture) {
    //TODO implement
    return null;
  }
}
