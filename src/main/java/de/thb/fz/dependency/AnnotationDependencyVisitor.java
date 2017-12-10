package de.thb.fz.dependency;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Type;

public class AnnotationDependencyVisitor extends AnnotationVisitor {

  private DependencyList dependencyList;

  AnnotationDependencyVisitor(DependencyList dependencyList) {
    super(327680);
    this.dependencyList = dependencyList;
  }

  public void visit(String name, Object value) {
    if (value instanceof Type) {
      dependencyList.addType((Type) value);
    }
  }

  public void visitEnum(String name, String desc, String value) {
    dependencyList.addDesc(desc);
  }

  public AnnotationVisitor visitAnnotation(String name, String desc) {
    dependencyList.addDesc(desc);
    return this;
  }

  public AnnotationVisitor visitArray(String name) {
    return this;
  }
}
