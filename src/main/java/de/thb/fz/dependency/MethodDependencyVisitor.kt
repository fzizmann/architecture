package de.thb.fz.dependency

import org.objectweb.asm.*
import org.objectweb.asm.signature.SignatureReader

class MethodDependencyVisitor internal constructor(private val dependencyList: DependencyList) : MethodVisitor(327680) {

    override fun visitAnnotationDefault(): AnnotationVisitor {
        return AnnotationDependencyVisitor(dependencyList)
    }

    override fun visitAnnotation(desc: String, visible: Boolean): AnnotationVisitor {
        dependencyList.addDesc(desc)
        return AnnotationDependencyVisitor(dependencyList)
    }

    override fun visitTypeAnnotation(typeRef: Int, typePath: TypePath, desc: String,
                                     visible: Boolean): AnnotationVisitor {
        dependencyList.addDesc(desc)
        return AnnotationDependencyVisitor(dependencyList)
    }

    override fun visitParameterAnnotation(parameter: Int, desc: String, visible: Boolean): AnnotationVisitor {
        dependencyList.addDesc(desc)
        return AnnotationDependencyVisitor(dependencyList)
    }

    override fun visitTypeInsn(opcode: Int, type: String) {
        dependencyList.addType(Type.getObjectType(type))
    }

    override fun visitFieldInsn(opcode: Int, owner: String, name: String, desc: String) {
        dependencyList.addInternalName(owner)
        dependencyList.addDesc(desc)
    }

    override fun visitMethodInsn(opcode: Int, owner: String, name: String, desc: String) {
        dependencyList.addInternalName(owner)
        dependencyList.addMethodDesc(desc)
    }

    override fun visitInvokeDynamicInsn(name: String, desc: String, bsm: Handle, vararg bsmArgs: Any) {
        dependencyList.addMethodDesc(desc)
        dependencyList.addConstant(bsm)

        for (bsmArg in bsmArgs) {
            dependencyList.addConstant(bsmArg)
        }

    }

    override fun visitLdcInsn(cst: Any) {
        dependencyList.addConstant(cst)
    }

    override fun visitMultiANewArrayInsn(desc: String, dims: Int) {
        dependencyList.addDesc(desc)
    }

    override fun visitInsnAnnotation(typeRef: Int, typePath: TypePath, desc: String,
                                     visible: Boolean): AnnotationVisitor {
        dependencyList.addDesc(desc)
        return AnnotationDependencyVisitor(dependencyList)
    }

    override fun visitLocalVariable(name: String, desc: String, signature: String?, start: Label,
                                    end: Label, index: Int) {
        if (signature != null) {
            SignatureReader(signature).acceptType(SignatureDependencyVisitor(dependencyList))
        }
    }

    override fun visitLocalVariableAnnotation(typeRef: Int, typePath: TypePath,
                                              start: Array<Label>, end: Array<Label>, index: IntArray, desc: String, visible: Boolean): AnnotationVisitor {
        dependencyList.addDesc(desc)
        return AnnotationDependencyVisitor(dependencyList)
    }

    override fun visitTryCatchBlock(start: Label, end: Label, handler: Label, type: String?) {
        if (type != null) {
            dependencyList.addInternalName(type)
        }
    }

    override fun visitTryCatchAnnotation(typeRef: Int, typePath: TypePath, desc: String,
                                         visible: Boolean): AnnotationVisitor {
        dependencyList.addDesc(desc)
        return AnnotationDependencyVisitor(dependencyList)
    }
}

