package de.fz.dsl;

import java.security.InvalidParameterException;

public class JavaImplementation {

  private String name;

  /**
   * @param name test
   */
  public JavaImplementation(String name) {
    this.name = name;
  }

  /**
   * test
   *
   * @param javaClass test
   * @return this
   */
  public static JavaImplementation javaImplementation(Class javaClass) {
    if (!javaClass.isInterface()) {
      return new JavaImplementation(javaClass.getName());
    } else {
      throw new InvalidParameterException(javaClass.getName() + " is a Interface.");
    }
  }

}
