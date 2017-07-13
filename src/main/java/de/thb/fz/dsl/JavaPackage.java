package de.thb.fz.dsl;

public class JavaPackage {

  private String packageName;

  public JavaPackage(String packageName) {
    this.packageName = packageName;
  }

  public static JavaPackage javaPackage(String name) {
    return new JavaPackage(name);
  }

  public String getPackageName() {
    return packageName;
  }
}
