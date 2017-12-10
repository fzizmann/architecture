package de.thb.fz.dependency;

import org.objectweb.asm.signature.SignatureVisitor;

public class SignatureDependencyVisitor extends SignatureVisitor {

  private String signatureClassName;

  private DependencyList dependencyList;

  SignatureDependencyVisitor(DependencyList dependencyList) {
    super(327680);
    this.dependencyList = dependencyList;
  }

  public void visitClassType(String name) {
    this.signatureClassName = name;
    dependencyList.addInternalName(name);
  }

  public void visitInnerClassType(String name) {
    this.signatureClassName = this.signatureClassName + "$" + name;
    dependencyList.addInternalName(this.signatureClassName);
  }
}