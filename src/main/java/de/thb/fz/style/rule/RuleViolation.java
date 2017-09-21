package de.thb.fz.style.rule;

import de.thb.fz.violation.Violation;

public class RuleViolation implements Violation {

  @Override
  public String getViolationMessage() {
    return "Rule Violation";
  }
}
