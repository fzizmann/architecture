package de.thb.fz.violation;

public class ArchitectureViolation implements Violation {

  private String violationMessage;

  public ArchitectureViolation(String violationMessage) {
    this.violationMessage = violationMessage;
  }

  @Override
  public String getViolationMessage() {
    return this.violationMessage;
  }
}
