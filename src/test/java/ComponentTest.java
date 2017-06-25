import static de.fz.dsl.JavaInterface.javaInterface;

import de.fz.dsl.Architecture;
import de.fz.dsl.Component;
import de.fz.dsl.JavaInterface;
import junit.framework.TestCase;

public class ComponentTest extends TestCase {

  public void testComponent() {
//
//    Component one = new Component("Test");
//
//    JavaInterface some = new JavaInterface("SomeClass");
//    JavaInterface other = javaInterface(Architecture.class);

    Class cl1 = Component.class;
    Class cls = Architecture.class;
    System.out.println(cls.isInterface());
    System.out.println(!cl1.isInterface());

  }

}
