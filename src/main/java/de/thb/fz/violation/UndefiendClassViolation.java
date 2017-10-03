package de.thb.fz.violation;

public class UndefiendClassViolation implements Violation {

  private String undefinedClass;

  public UndefiendClassViolation(String undefinedClass) {
    this.undefinedClass = undefinedClass;
  }

  @Override
  public String getViolationMessage() {
    return "Klasse " + this.undefinedClass + " wurde nicht in der Architektur definiert.";
  }

  @Override
  public String toString() {
    return this.getViolationMessage();
  }
}
