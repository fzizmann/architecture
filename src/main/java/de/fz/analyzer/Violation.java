package de.fz.analyzer;

public class Violation {

  /**
   * Component in which violation happened
   */
  private String component;
  /**
   * Class that violates rules
   */
  private String violatingClass;
  /**
   * Class which was ued
   */
  private String violatedClass;

  public Violation(String component, String violatedClass) {
    this.component = component;
    this.violatedClass = violatedClass;
  }

  public String getComponent() {
    return component;
  }

  public Violation setComponent(String component) {
    this.component = component;
    return this;
  }

  public String getViolatingClass() {
    return violatingClass;
  }

  public Violation setViolatingClass(String violatingClass) {
    this.violatingClass = violatingClass;
    return this;
  }

  public String getViolatedClass() {
    return violatedClass;
  }

  public Violation setViolatedClass(String violatedClass) {
    this.violatedClass = violatedClass;
    return this;
  }
}
