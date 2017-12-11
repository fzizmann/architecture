package de.thb.fz.dependency;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;
import org.objectweb.asm.TypePath;
import org.objectweb.asm.signature.SignatureReader;

public class DependencyVisitor extends ClassVisitor {

  private DependencyList dependencyList;

  public DependencyVisitor(DependencyList dependencyList) {
    super(327680);
    this.dependencyList = dependencyList;
  }

  public void visit(int version, int access, String name, String signature, String superName,
      String[] interfaces) {
    if (signature == null) {
      if (superName != null) {
        this.dependencyList.addInternalName(superName);
      }
      this.dependencyList.addInternalNames(interfaces);
    } else {
      (new SignatureReader(signature)).accept(new SignatureDependencyVisitor(dependencyList));
    }

  }

  public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
    this.dependencyList.addDesc(desc);
    return new AnnotationDependencyVisitor(dependencyList);
  }

  public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String desc,
      boolean visible) {
    this.dependencyList.addDesc(desc);
    return new AnnotationDependencyVisitor(dependencyList);
  }

  public FieldVisitor visitField(int access, String name, String desc, String signature,
      Object value) {
    if (signature == null) {
      this.dependencyList.addDesc(desc);
    } else {
      (new SignatureReader(signature)).acceptType(new SignatureDependencyVisitor(dependencyList));
    }
    if (value instanceof Type) {
      this.dependencyList.addType((Type) value);
    }

    return new FieldDependencyVisitor(dependencyList);
  }

  public MethodVisitor visitMethod(int access, String name, String desc, String signature,
      String[] exceptions) {
    if (signature == null) {
      this.dependencyList.addMethodDesc(desc);
    } else {
      (new SignatureReader(signature)).accept(new SignatureDependencyVisitor(dependencyList));
    }

    this.dependencyList.addInternalNames(exceptions);
    return new MethodDependencyVisitor(dependencyList);
  }
}
