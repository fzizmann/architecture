package de.thb.fz.style.pattern;

import de.thb.fz.violation.Violation;

public class PatternViolation implements Violation {

  private String violationMessage;

  PatternViolation(String violationMessage) {
    this.violationMessage = violationMessage;
  }

  public String getViolationMessage() {
    return violationMessage;
  }
}
