package de.thb.fz.dependency;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.TypePath;

public class FieldDependencyVisitor extends FieldVisitor {

  private DependencyList dependencyList;

  public FieldDependencyVisitor(DependencyList dependencyList) {
    super(327680);
    this.dependencyList = dependencyList;
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
}
