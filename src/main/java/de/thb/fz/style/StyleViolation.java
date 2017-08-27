package de.thb.fz.style;

public class StyleViolation {

  private String violationMessage;

  StyleViolation(String violationMessage) {
    this.violationMessage = violationMessage;
  }

  public String getViolationMessage() {
    return violationMessage;
  }
}
