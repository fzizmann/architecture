package de.fz.dependency;

import org.objectweb.asm.ClassReader;

import java.io.IOException;
import java.util.Map;

public class DependencyTracker {

    public Map<String, Integer> loadDependencies(Class clazz) throws IOException {
        DependencyVisitor classVisitor = new DependencyVisitor();
        String className = clazz.getName();
        new ClassReader(className).accept(classVisitor, 0);
        // hier noch die globals direkt übergeb
        return classVisitor.getGlobals().get(className.replace('.', '/'));
    }
}
