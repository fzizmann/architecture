package de.fz.dsl;

import java.security.InvalidParameterException;

public class JavaInterface {


  private String name;

  public JavaInterface(String name) {
    this.name = name;
  }

  /**
   * @param javaClass
   * @return
   */
  public static JavaInterface javaInterface(Class javaClass) {
    if (javaClass.isInterface()) {
      return new JavaInterface(javaClass.getName());
    } else {
      throw new InvalidParameterException(javaClass.getName() + " is not a Interface.");
    }
  }

}
