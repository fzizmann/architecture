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

  public static Rule uses(String typeA, String typeB) {

    return new UsesRule(typeA, typeB);
  }

  @Override
  public ArrayList<Violation> execute(Architecture architecture) {
    return null;
  }
}
