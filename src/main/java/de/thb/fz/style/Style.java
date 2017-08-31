package de.thb.fz.style;

import de.thb.fz.dsl.Architecture;
import java.util.ArrayList;

public interface Style {

  ArrayList<StyleViolation> validate(Architecture architecture);

}
