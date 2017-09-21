package de.thb.fz.style.pattern;

import de.thb.fz.dsl.Architecture;
import java.util.ArrayList;

public interface Style {

  ArrayList<PatternViolation> validate(Architecture architecture);

}
