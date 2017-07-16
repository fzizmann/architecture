package de.thb.fz.dsl;

public class JavaPackage {

  private String packageName;
  private String file;
  private String className;

  public JavaPackage(String packageName) {
    this.packageName = packageName;
  }

  public static JavaPackage javaPackage(String name) {
    return new JavaPackage(name);
  }

  public String getPackageName() {
    return packageName;
  }


  public void setPackageName(String packageName) {
    this.packageName = packageName;
  }

  public String getClassName() {
    return className;
  }

  public void setClassName(String className) {
    this.className = className;
  }

  public String getFile() {
    return file;
  }

  public void setFile(String file) {
    this.file = file;
  }
}
