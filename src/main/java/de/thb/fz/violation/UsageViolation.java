package de.thb.fz.violation;

public class UsageViolation implements Violation {

  @Override
  public String getViolationMessage() {
    return "UsageViolation";
  }
}
