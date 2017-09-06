package de.thb.example;

import static de.thb.fz.dsl.Architecture.architecture;
import static de.thb.fz.dsl.Component.component;

import de.thb.fz.dsl.Architecture;
import de.thb.fz.dsl.ArchitectureDescription;
import de.thb.fz.style.Mvc;

/**
 * Beispieldefinition einer Architektur
 */
public class ArchitectureExample implements ArchitectureDescription {

  @Override
  public Architecture defineArchitecture() {
    return architecture(
        component("model")
            .structure(
                "de.thb.fz.analyzer"
            ).type(Mvc.MODEL)
        , component("controller")
            .structure(
                "de.thb.fz.dsl"
            )
            .type(Mvc.CONTROLLER)
        , component("view")
            .structure(
                "de.thb.fz.style"
            ).type(Mvc.VIEW)
    ).styles(
        new Mvc()
    );
  }
}
