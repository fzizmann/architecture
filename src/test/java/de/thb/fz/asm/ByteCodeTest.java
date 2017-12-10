package de.thb.fz.asm;

import de.thb.fz.analyzer.ArchitectureAnalyserTest;
import java.io.IOException;
import java.io.InputStream;
import org.junit.Test;
import org.objectweb.asm.ClassReader;

public class ByteCodeTest {

  @Test
  public void loadClassAndTestVisitor() {
    TestVisitor tv = new TestVisitor();
    InputStream stream = this.getClass().getClassLoader().getResourceAsStream(
        ArchitectureAnalyserTest.class.getName().replace('.', '/') + ".class"
    );
    try {
      ClassReader classReader = new ClassReader(stream);
      classReader.accept(tv, 0);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
