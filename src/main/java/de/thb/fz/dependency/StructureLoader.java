package de.thb.fz.dependency;


import de.thb.fz.dsl.Architecture;
import de.thb.fz.dsl.Component;

public class StructureLoader {

  public void loadStructures(Architecture architectureDescription) {
    for (Component comp : architectureDescription.getComponents()) {

      //1. aus jeder Komponente JavaPackages abfragen

      //2. Packages und Klassen laden
      //  i.es muss jede Klasse durchlafen werden
      //  ii. jede Dependency der Klasse muss geprüft werden, ob diese auf eine andere Komponente zugreift
      //3. ablegen in graphiz Struktur?
      // Ziel Struktur:
      // Componente A greift auf Liste<Componenten>
      // Componente B greift auf Liste<Componenten>
      // ...
    }

    // 1. Iteration: aufbau eines Komponentenbaums
    // 2. Ietration: Interfaces und Implementierungen werden als "erlaubte" Verbindungen markiert
  }

}
