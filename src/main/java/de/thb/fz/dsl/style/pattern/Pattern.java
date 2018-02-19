package de.thb.fz.dsl.style.pattern;

import de.thb.fz.dsl.Architecture;
import de.thb.fz.violation.Violation;
import java.util.ArrayList;

public interface Pattern {

  ArrayList<Violation> validate(Architecture architecture);

}
