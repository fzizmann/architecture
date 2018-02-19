package de.thb.fz.violation;

import de.thb.fz.violation.Violation;

public class PatternViolation implements Violation {

  private String violationMessage;

  public PatternViolation(String violationMessage) {
    this.violationMessage = violationMessage;
  }

  public String getViolationMessage() {
    return violationMessage;
  }
}
