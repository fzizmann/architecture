package de.thb.fz.style;

import de.thb.fz.analyzer.ComponentIndex;
import java.util.ArrayList;

public interface Style {

  ArrayList<StyleViolation> validate(ComponentIndex componentIndex);

}
