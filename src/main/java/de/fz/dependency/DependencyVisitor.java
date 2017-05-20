package de.fz.dependency;

import org.objectweb.asm.*;
import org.objectweb.asm.signature.SignatureReader;
import org.objectweb.asm.signature.SignatureVisitor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DependencyVisitor extends ClassVisitor {
    Set<String> packages = new HashSet();
    Map<String, Map<String, Integer>> groups = new HashMap();
    Map<String, Integer> current;

    public Map<String, Map<String, Integer>> getGlobals() {
        return this.groups;
    }

    public Set<String> getPackages() {
        return this.packages;
    }

    public DependencyVisitor() {
        super(327680);
    }

    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        String p = this.getGroupKey(name);
        this.current = (Map) this.groups.get(p);
        if (this.current == null) {
            this.current = new HashMap();
            this.groups.put(p, this.current);
        }

        if (signature == null) {
            if (superName != null) {
                this.addInternalName(superName);
            }

            this.addInternalNames(interfaces);
        } else {
            this.addSignature(signature);
        }

    }

    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        this.addDesc(desc);
        return new DependencyVisitor.AnnotationDependencyVisitor();
    }

    public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
        this.addDesc(desc);
        return new DependencyVisitor.AnnotationDependencyVisitor();
    }

    public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
        if (signature == null) {
            this.addDesc(desc);
        } else {
            this.addTypeSignature(signature);
        }

        if (value instanceof Type) {
            this.addType((Type) value);
        }

        return new DependencyVisitor.FieldDependencyVisitor();
    }

    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        if (signature == null) {
            this.addMethodDesc(desc);
        } else {
            this.addSignature(signature);
        }

        this.addInternalNames(exceptions);
        return new DependencyVisitor.MethodDependencyVisitor();
    }

    private String getGroupKey(String name) {
        this.packages.add(name);
        return name;
    }

    private void addName(String name) {
        if (name != null) {
            String p = this.getGroupKey(name);
            p = p.replace('/', '.');
            if (this.current.containsKey(p)) {
                this.current.put(p, Integer.valueOf(((Integer) this.current.get(p)).intValue() + 1));
            } else {
                this.current.put(p, Integer.valueOf(1));
            }

        }
    }

    void addInternalName(String name) {
        this.addType(Type.getObjectType(name));
    }

    private void addInternalNames(String[] names) {
        for (int i = 0; names != null && i < names.length; ++i) {
            this.addInternalName(names[i]);
        }

    }

    void addDesc(String desc) {
        this.addType(Type.getType(desc));
    }

    void addMethodDesc(String desc) {
        this.addType(Type.getReturnType(desc));
        Type[] types = Type.getArgumentTypes(desc);

        for (int i = 0; i < types.length; ++i) {
            this.addType(types[i]);
        }

    }

    void addType(Type t) {
        switch (t.getSort()) {
            case 9:
                this.addType(t.getElementType());
                break;
            case 10:
                this.addName(t.getInternalName());
                break;
            case 11:
                this.addMethodDesc(t.getDescriptor());
        }

    }

    private void addSignature(String signature) {
        if (signature != null) {
            (new SignatureReader(signature)).accept(new DependencyVisitor.SignatureDependencyVisitor());
        }

    }

    void addTypeSignature(String signature) {
        if (signature != null) {
            (new SignatureReader(signature)).acceptType(new DependencyVisitor.SignatureDependencyVisitor());
        }

    }

    void addConstant(Object cst) {
        if (cst instanceof Type) {
            this.addType((Type) cst);
        } else if (cst instanceof Handle) {
            Handle h = (Handle) cst;
            this.addInternalName(h.getOwner());
            this.addMethodDesc(h.getDesc());
        }

    }

    class AnnotationDependencyVisitor extends AnnotationVisitor {
        public AnnotationDependencyVisitor() {
            super(327680);
        }

        public void visit(String name, Object value) {
            if (value instanceof Type) {
                DependencyVisitor.this.addType((Type) value);
            }

        }

        public void visitEnum(String name, String desc, String value) {
            DependencyVisitor.this.addDesc(desc);
        }

        public AnnotationVisitor visitAnnotation(String name, String desc) {
            DependencyVisitor.this.addDesc(desc);
            return this;
        }

        public AnnotationVisitor visitArray(String name) {
            return this;
        }

        class Another {
            String a;
            String b;

            public Another(String a, String b) {
                this.a = a;
                this.b = b;
            }

            public String getA() {
                return this.a;
            }

            public void setA(String a) {
                this.a = a;
            }

            public String getB() {
                return this.b;
            }

            public void setB(String b) {
                this.b = b;
            }
        }
    }

    class FieldDependencyVisitor extends FieldVisitor {
        public FieldDependencyVisitor() {
            super(327680);
        }

        public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
            DependencyVisitor.this.addDesc(desc);
            return DependencyVisitor.this.new AnnotationDependencyVisitor();
        }

        public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
            DependencyVisitor.this.addDesc(desc);
            return DependencyVisitor.this.new AnnotationDependencyVisitor();
        }
    }

    class MethodDependencyVisitor extends MethodVisitor {
        public MethodDependencyVisitor() {
            super(327680);
        }

        public AnnotationVisitor visitAnnotationDefault() {
            return DependencyVisitor.this.new AnnotationDependencyVisitor();
        }

        public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
            DependencyVisitor.this.addDesc(desc);
            return DependencyVisitor.this.new AnnotationDependencyVisitor();
        }

        public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
            DependencyVisitor.this.addDesc(desc);
            return DependencyVisitor.this.new AnnotationDependencyVisitor();
        }

        public AnnotationVisitor visitParameterAnnotation(int parameter, String desc, boolean visible) {
            DependencyVisitor.this.addDesc(desc);
            return DependencyVisitor.this.new AnnotationDependencyVisitor();
        }

        public void visitTypeInsn(int opcode, String type) {
            DependencyVisitor.this.addType(Type.getObjectType(type));
        }

        public void visitFieldInsn(int opcode, String owner, String name, String desc) {
            DependencyVisitor.this.addInternalName(owner);
            DependencyVisitor.this.addDesc(desc);
        }

        public void visitMethodInsn(int opcode, String owner, String name, String desc) {
            DependencyVisitor.this.addInternalName(owner);
            DependencyVisitor.this.addMethodDesc(desc);
        }

        public void visitInvokeDynamicInsn(String name, String desc, Handle bsm, Object... bsmArgs) {
            DependencyVisitor.this.addMethodDesc(desc);
            DependencyVisitor.this.addConstant(bsm);

            for (int i = 0; i < bsmArgs.length; ++i) {
                DependencyVisitor.this.addConstant(bsmArgs[i]);
            }

        }

        public void visitLdcInsn(Object cst) {
            DependencyVisitor.this.addConstant(cst);
        }

        public void visitMultiANewArrayInsn(String desc, int dims) {
            DependencyVisitor.this.addDesc(desc);
        }

        public AnnotationVisitor visitInsnAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
            DependencyVisitor.this.addDesc(desc);
            return DependencyVisitor.this.new AnnotationDependencyVisitor();
        }

        public void visitLocalVariable(String name, String desc, String signature, Label start, Label end, int index) {
            DependencyVisitor.this.addTypeSignature(signature);
        }

        public AnnotationVisitor visitLocalVariableAnnotation(int typeRef, TypePath typePath, Label[] start, Label[] end, int[] index, String desc, boolean visible) {
            DependencyVisitor.this.addDesc(desc);
            return DependencyVisitor.this.new AnnotationDependencyVisitor();
        }

        public void visitTryCatchBlock(Label start, Label end, Label handler, String type) {
            if (type != null) {
                DependencyVisitor.this.addInternalName(type);
            }

        }

        public AnnotationVisitor visitTryCatchAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
            DependencyVisitor.this.addDesc(desc);
            return DependencyVisitor.this.new AnnotationDependencyVisitor();
        }
    }

    class SignatureDependencyVisitor extends SignatureVisitor {
        String signatureClassName;

        public SignatureDependencyVisitor() {
            super(327680);
        }

        public void visitClassType(String name) {
            this.signatureClassName = name;
            DependencyVisitor.this.addInternalName(name);
        }

        public void visitInnerClassType(String name) {
            this.signatureClassName = this.signatureClassName + "$" + name;
            DependencyVisitor.this.addInternalName(this.signatureClassName);
        }
    }
}
