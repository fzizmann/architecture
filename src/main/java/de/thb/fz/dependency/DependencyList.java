package de.thb.fz.dependency;

import java.util.HashMap;
import java.util.HashSet;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Type;

class DependencyList {

  private HashSet<String> packages = new HashSet<>();
  private HashMap<String, HashMap<String, Integer>> groups = new HashMap<>();
  private HashMap<String, Integer> current;

  HashMap<String, HashMap<String, Integer>> getGlobals() {
    return this.groups;
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
        this.current.put(p, this.current.get(p) + 1);
      } else {
        this.current.put(p, 1);
      }

    }
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
        this.addName(t.getInternalName());
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

  void initializeCurrent(String name) {
    this.current = this.groups.computeIfAbsent(this.getGroupKey(name), k -> new HashMap<>());
  }
}
