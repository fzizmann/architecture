import java.io.IOException;
import jdepend.framework.DependencyConstraint;
import jdepend.framework.JDepend;
import jdepend.framework.JavaPackage;
import junit.framework.TestCase;

public class ConstraintTest extends TestCase {

  private JDepend jdepend;

  protected void setUp() throws IOException {
    jdepend = new JDepend();

    System.out.println("Working Directory = " +
        System.getProperty("user.dir"));
    jdepend.addDirectory(System.getProperty("user.dir") + "\\target\\classes");
  }

  /**
   * Tests the package dependency constraint
   * for the analyzed packages.
   */
  public void testMatch() {

    DependencyConstraint constraint = new DependencyConstraint();

    constraint.addPackage("java.util");
    constraint.addPackage("jdepend.framework");
    constraint.addPackage("org.reflections.util");
    constraint.addPackage("org.reflections");
    constraint.addPackage("java.lang");
    constraint.addPackage("java.io");
    constraint.addPackage("junit.textui");
    constraint.addPackage("junit.framework");
    constraint.addPackage("org.reflections.scanners");

    System.out.println("Classes: " + jdepend.countClasses());
    System.out.println("Packages: " + jdepend.countPackages());
    for (Object item : jdepend.analyze()) {
      System.out
          .println(((JavaPackage) item).getName() + " " + ((JavaPackage) item).afferentCoupling());
    }
    System.out.println("Dependency mismatch " + jdepend.dependencyMatch(constraint));
  }
}
