package de.fz.jdepend;

import jdepend.framework.DependencyConstraint;
import jdepend.framework.JDepend;
import jdepend.framework.JavaPackage;
import junit.framework.TestCase;

import java.io.IOException;

public class ConstraintTest extends TestCase {
    private JDepend jdepend;

    public ConstraintTest(String name) {
        super(name);
    }

    protected void setUp() throws IOException {
        jdepend = new JDepend();

        System.out.println("Working Directory = " +
            System.getProperty("user.dir"));
        jdepend.addDirectory(System.getProperty("user.dir")+ "\\target\\classes");
    }

    /**
     * Tests the package dependency constraint
     * for the analyzed packages.
     */
    public void testMatch() {

        DependencyConstraint constraint = new DependencyConstraint();

        JavaPackage analyzer = constraint.addPackage("de.fz.analyzer");
        JavaPackage dsl = constraint.addPackage("de.fz.dsl");
        JavaPackage test = constraint.addPackage("test");
        JavaPackage test1 = constraint.addPackage("Lulu");
        JavaPackage test2 = constraint.addPackage("java.util");
        JavaPackage test3 = constraint.addPackage("jdepend.framework");
        JavaPackage test4 = constraint.addPackage("de.fz.jdepend");
        JavaPackage test5 = constraint.addPackage("org.reflections.util");
        JavaPackage test6 = constraint.addPackage("org.reflections");
        JavaPackage test7 = constraint.addPackage("java.lang");
        JavaPackage test8 = constraint.addPackage("java.io");
        JavaPackage test9 = constraint.addPackage("junit.textui");
        JavaPackage test10 = constraint.addPackage("junit.framework");
        JavaPackage test11 = constraint.addPackage("org.reflections.scanners");

        analyzer.dependsUpon(test);
        dsl.dependsUpon(test);
        test3.dependsUpon(analyzer);

        jdepend.analyze();

        System.out.println("Dependency mismatch " + jdepend.dependencyMatch(constraint));
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(ConstraintTest.class);
    }

}
