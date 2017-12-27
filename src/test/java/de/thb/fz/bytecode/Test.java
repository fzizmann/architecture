package de.thb.fz.bytecode;

import java.util.ArrayList;
import javax.annotation.Untainted;
import javax.xml.ws.soap.Addressing;

@Addressing
public class Test
//    <@Encrypt(author = {"asd","author2"}, description = "test", enume = En.APPLE, scriptingLang = @Another("Groovy")) String>

{

  ArrayList<ClassReference> classReferenceTest;

  Type test;

  public FieldReference test(@Untainted MethodReference reference) {
    return new FieldReference(reference);
  }

  class Type {

  }
}
