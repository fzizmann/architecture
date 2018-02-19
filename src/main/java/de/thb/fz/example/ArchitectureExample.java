package de.thb.fz.example;

import static de.thb.fz.dsl.Architecture.architecture;
import static de.thb.fz.dsl.Component.component;
import static de.thb.fz.dsl.style.pattern.Mvc.mvc;
import static de.thb.fz.dsl.style.rule.UsesRule.disallowUsage;

import de.thb.fz.dsl.Architecture;
import de.thb.fz.dsl.ArchitectureDescription;
import de.thb.fz.violation.PatternViolation;
import de.thb.fz.violation.UnusedInterfaceViolation;
import de.thb.fz.violation.Violation;

/**
 * Beispieldefinition einer Architektur
 */
public class ArchitectureExample implements ArchitectureDescription {

  @Override
  public Architecture defineArchitecture() {
    return architecture(
        component("violation")
            .structure(
                "de.thb.fz.violation"
            )
            .interfaces(Violation.class, UnusedInterfaceViolation.class)
            .type("controller")
        , component("style")
            .structure(
                "de.thb.fz.dsl.style"
            )
            .implementations(PatternViolation.class)
            .type("view")
    ).pattern(
        mvc()
    ).rules(
        disallowUsage("view", "controller")
    );
  }
}
