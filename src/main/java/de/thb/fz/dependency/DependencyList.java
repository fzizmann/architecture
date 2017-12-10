package de.thb.fz.dependency;

import java.util.HashSet;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Type;

class DependencyList {

  private HashSet<String> dependencies;

  DependencyList() {
    this.dependencies = new HashSet<>();
  }

  HashSet<String> getDependencies() {
    return dependencies;
  }

  void addInternalName(String name) {
    this.addType(Type.getObjectType(name));
  }

  void addMethodDesc(String desc) {
    this.addType(Type.getReturnType(desc));
    Type[] types = Type.getArgumentTypes(desc);

    for (Type type : types) {
      this.addType(type);
    }
  }

  void addType(Type t) {
    switch (t.getSort()) {
      case 9:
        this.addType(t.getElementType());
        break;
      case 10:
        if (t.getInternalName() != null) {
          this.dependencies.add(t.getInternalName().replace('/', '.'));
        }
        break;
      case 11:
        this.addMethodDesc(t.getDescriptor());
    }
  }

  void addInternalNames(String[] names) {
    for (int i = 0; names != null && i < names.length; ++i) {
      this.addInternalName(names[i]);
    }
  }

  void addDesc(String desc) {
    this.addType(Type.getType(desc));
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
}
