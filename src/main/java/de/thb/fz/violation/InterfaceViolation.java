package de.thb.fz.violation;

public class InterfaceViolation implements Violation {

  private String interfaceClass;

  public InterfaceViolation(String interfaceClass) {
    this.interfaceClass = interfaceClass;
  }

  @Override
  public String getViolationMessage() {
    return "Das defnierte Interface " + this.interfaceClass + " ist eine Klasse.";
  }

  @Override
  public String toString() {
    return this.getViolationMessage();
  }
}
