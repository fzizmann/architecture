package de.thb.fz.violation;

import de.thb.fz.violation.Violation;

public class RuleViolation implements Violation {

  private String message;

  public RuleViolation(String message) {
    this.message = message;
  }

  @Override
  public String getViolationMessage() {
    return message;
  }
}
