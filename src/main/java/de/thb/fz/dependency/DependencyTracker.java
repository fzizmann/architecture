package de.thb.fz.dependency;

import java.io.IOException;
import java.util.Map;
import org.objectweb.asm.ClassReader;

public class DependencyTracker {

    public Map<String, Integer> loadDependencies(Class clazz) throws IOException {
        DependencyVisitor classVisitor = new DependencyVisitor();
        String className = clazz.getName();
        new ClassReader(className).accept(classVisitor, 0);
        return classVisitor.getGlobals().get(className.replace('.', '/'));
    }
}
