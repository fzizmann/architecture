package de.thb.fz.dsl.style.rule;

import de.thb.fz.dsl.Architecture;
import de.thb.fz.violation.Violation;
import java.util.ArrayList;

public interface Rule {

  public ArrayList<Violation> execute(Architecture architecture);
}
