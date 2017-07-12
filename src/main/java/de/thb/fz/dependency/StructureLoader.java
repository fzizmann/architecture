package de.thb.fz.dependency;


import de.thb.fz.dsl.ArchitectureDescription;
import de.thb.fz.dsl.Component;

public class StructureLoader {

  public void loadStructures(ArchitectureDescription architectureDescription) {
    for (Component comp : architectureDescription.getComponents()) {
      //1. aus jeder Komponenten JavaPackages abfragen
      //2. Packages und Klassen laden
      //3. ablegen in Struktur Komponente => Klassen
    }

    //erste Klassen Iteration: es werden alle Klassen entfernt, die nur Referenzen innheralb der Komponente haben
    // dazu müssen die Klassen vom DependencyVisitor durchlaufen werden
  }

}
