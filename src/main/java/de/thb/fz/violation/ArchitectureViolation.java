package de.thb.fz.violation;

public class ArchitectureViolation implements Violation {

  private String sourceClass;
  private String targetClass;
  private String sourceComponent;
  private String targetComponent;

  public ArchitectureViolation(String sourceClass, String targetClass,
      String sourceComponent, String targetComponent) {
    this.sourceClass = sourceClass;
    this.targetClass = targetClass;
    this.sourceComponent = sourceComponent;
    this.targetComponent = targetComponent;
  }

  @Override
  public String getViolationMessage() {
    return "Klasse " + this.sourceClass + " in " +
        this.sourceComponent + " benutzt Klasse " + targetClass +
        " in Komponente " + targetComponent;
  }

  @Override
  public String toString() {
    return this.getViolationMessage();
  }
}
