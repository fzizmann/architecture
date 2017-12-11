package de.thb.fz.asm;

import de.thb.fz.dependency.DependencyList;
import de.thb.fz.dependency.DependencyVisitor;
import java.io.IOException;
import java.io.InputStream;
import org.junit.Test;
import org.objectweb.asm.ClassReader;

public class ByteCodeTest {

  @Test
  public void loadClassAndTestVisitor() {
    DependencyVisitor dv = new DependencyVisitor(new DependencyList());
    InputStream stream = this.getClass().getClassLoader().getResourceAsStream(
        de.thb.fz.bytecode.Test.class.getName().replace('.', '/') + ".class"
    );
    try {
      ClassReader classReader = new ClassReader(stream);
      classReader.accept(dv, 0);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
