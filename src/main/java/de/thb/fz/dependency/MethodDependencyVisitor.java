package de.thb.fz.dependency;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;
import org.objectweb.asm.TypePath;
import org.objectweb.asm.signature.SignatureReader;

class MethodDependencyVisitor extends MethodVisitor {

  private DependencyList dependencyList;

  MethodDependencyVisitor(DependencyList dependencyList) {
    super(327680);
    this.dependencyList = dependencyList;
  }

  public AnnotationVisitor visitAnnotationDefault() {
    return new AnnotationDependencyVisitor(dependencyList);
  }

  public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
    dependencyList.addDesc(desc);
    return new AnnotationDependencyVisitor(dependencyList);
  }

  public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String desc,
      boolean visible) {
    dependencyList.addDesc(desc);
    return new AnnotationDependencyVisitor(dependencyList);
  }

  public AnnotationVisitor visitParameterAnnotation(int parameter, String desc, boolean visible) {
    dependencyList.addDesc(desc);
    return new AnnotationDependencyVisitor(dependencyList);
  }

  public void visitTypeInsn(int opcode, String type) {
    dependencyList.addType(Type.getObjectType(type));
  }

  public void visitFieldInsn(int opcode, String owner, String name, String desc) {
    dependencyList.addInternalName(owner);
    dependencyList.addDesc(desc);
  }

  public void visitMethodInsn(int opcode, String owner, String name, String desc) {
    dependencyList.addInternalName(owner);
    dependencyList.addMethodDesc(desc);
  }

  public void visitInvokeDynamicInsn(String name, String desc, Handle bsm, Object... bsmArgs) {
    dependencyList.addMethodDesc(desc);
    dependencyList.addConstant(bsm);
    for (Object bsmArg : bsmArgs) {
      dependencyList.addConstant(bsmArg);
    }
  }

  public void visitLdcInsn(Object cst) {
    dependencyList.addConstant(cst);
  }

  public void visitMultiANewArrayInsn(String desc, int dims) {
    dependencyList.addDesc(desc);
  }

  public AnnotationVisitor visitInsnAnnotation(int typeRef, TypePath typePath, String desc,
      boolean visible) {
    dependencyList.addDesc(desc);
    return new AnnotationDependencyVisitor(dependencyList);
  }

  public void visitLocalVariable(String name, String desc, String signature, Label start, Label end,
      int index) {
    if (signature != null) {
      (new SignatureReader(signature)).acceptType(new SignatureDependencyVisitor(dependencyList));
    }
  }

  public AnnotationVisitor visitLocalVariableAnnotation(int typeRef, TypePath typePath,
      Label[] start, Label[] end, int[] index, String desc, boolean visible) {
    dependencyList.addDesc(desc);
    return new AnnotationDependencyVisitor(dependencyList);
  }

  public void visitTryCatchBlock(Label start, Label end, Label handler, String type) {
    if (type != null) {
      dependencyList.addInternalName(type);
    }
  }

  public AnnotationVisitor visitTryCatchAnnotation(int typeRef, TypePath typePath, String desc,
      boolean visible) {
    dependencyList.addDesc(desc);
    return new AnnotationDependencyVisitor(dependencyList);
  }
}

