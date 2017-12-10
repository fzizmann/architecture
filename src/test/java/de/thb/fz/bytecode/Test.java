package de.thb.fz.bytecode;

import java.util.ArrayList;

public class Test {

  /**
   * @see AnnotationReference
   */
  private ArrayList<ClassReference> classReferenceTest;

  public FieldReference test(MethodReference reference) {
    return new FieldReference(reference);
  }

  class SubClassReference {

    private String test;
  }
}
