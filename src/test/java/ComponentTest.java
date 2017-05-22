import static de.fz.dsl.JavaInterface.javaInterface;

import de.fz.dsl.Architecture;
import de.fz.dsl.Component;
import de.fz.dsl.JavaInterface;

public class ComponentTest {

  public void testComponent() {

    Component one = new Component("Test");

    JavaInterface some = new JavaInterface("SomeClass");
    JavaInterface other = javaInterface(Architecture.class);

  }

}
