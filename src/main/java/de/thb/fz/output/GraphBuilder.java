package de.thb.fz.output;

import de.thb.fz.analyzer.ComponentNode;
import de.thb.fz.analyzer.ComponentTree;

public class GraphBuilder {

  /**
   * Erstellt eine Graphen definition aus einem Komponentenbaum.
   */
  public static String drawGraph(ComponentTree tree) {

    StringBuilder graph = new StringBuilder("digraph junit { \n");
    for (ComponentNode componentNode : tree.getComponentList()) {
      for (String conn : componentNode.getConnection()) {
        graph.append(componentNode.getComponent().getComponentName()).append(" -> ")
            .append(conn).append(";\n");
      }
    }
    return graph.append("}").toString();
  }

}
