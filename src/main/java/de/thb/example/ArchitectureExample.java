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
                "de.app.model"
            ).type(Mvc.MODEL)
        , component("controller")
            .structure(
                "de.app.controller"
            )
            .type(Mvc.CONTROLLER)
        , component("view")
            .structure(
                "de.app.view"
            ).type(Mvc.VIEW)
    ).styles(
        new Mvc()
    );
  }
}
