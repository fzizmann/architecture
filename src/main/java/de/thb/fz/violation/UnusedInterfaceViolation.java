package de.thb.fz.violation;

public class UnusedInterfaceViolation implements Violation {

  private String interfaceClass;

  public UnusedInterfaceViolation(String interfaceClass) {
    this.interfaceClass = interfaceClass;
  }

  @Override
  public String getViolationMessage() {
    return "Klasse " + this.interfaceClass + " wurde als Interface definiert aber nicht benutzt.";
  }

  @Override
  public String toString() {
    return this.getViolationMessage();
  }
}
