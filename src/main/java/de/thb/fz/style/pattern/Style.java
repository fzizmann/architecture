package de.thb.fz.style.pattern;

import de.thb.fz.dsl.Architecture;
import de.thb.fz.violation.Violation;
import java.util.ArrayList;

public interface Style {

  ArrayList<Violation> validate(Architecture architecture);

}
