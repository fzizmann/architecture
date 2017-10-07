package de.thb.fz.example;

import static de.thb.fz.dsl.Architecture.architecture;
import static de.thb.fz.dsl.Component.component;

import de.thb.fz.dsl.Architecture;
import de.thb.fz.dsl.ArchitectureDescription;
import de.thb.fz.style.pattern.Mvc;
import de.thb.fz.style.pattern.PatternViolation;
import de.thb.fz.style.rule.SubComponentRule;
import de.thb.fz.style.rule.UsesRule;
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
            .interfaces(Violation.class)
        , component("style")
            .structure(
                "de.thb.fz.style"
            )
            .implementations(PatternViolation.class)
    ).styles(
        new Mvc()
    ).rules(
        UsesRule.notAllowed("View", "Model"),
        SubComponentRule.subComponentOf("ComponentOne", "ComponentOne")
    );
  }
}
