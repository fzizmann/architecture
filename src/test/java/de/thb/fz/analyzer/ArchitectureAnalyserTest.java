package de.thb.fz.analyzer;

import de.thb.fz.dependency.DependencyLoader;
import de.thb.fz.dsl.Architecture;
import de.thb.fz.dsl.builder.ArchitectureBuilder;
import de.thb.fz.example.ArchitectureExample;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

public class ArchitectureAnalyserTest {

  private Architecture architecture;
  private ArchitectureAnalyser architectureAnalyser;

  @Before
  public void setUp() throws Exception {
    architectureAnalyser = new ArchitectureAnalyser();
    architecture = new ArchitectureExample().defineArchitecture();
    new ArchitectureBuilder(new DependencyLoader()).accumulateArchitecture(architecture);
  }

  @Test
  public void analyzeInterfaceAndImplementation() throws Exception {
    architectureAnalyser.analyzeInterfaceAndImplementation(architecture)
        .forEach(System.out::println);
  }

  @Test
  public void analyzeUnusedInterfaces() throws Exception {
    architectureAnalyser.analyzeUnusedInterfaces(architecture)
        .forEach(System.out::println);
  }

  @Test
  public void analyzeUndefinedClasses() throws Exception {
    architectureAnalyser.analyzeUndefinedClasses(architecture,
        new ArrayList<>(new DependencyLoader().generateClassList("de.thb")))
        .forEach(System.out::println);
  }

  @Test
  public void analyzeStyle() throws Exception {
    architectureAnalyser.analyzeStyle(architecture)
        .forEach(violation -> System.out.println(violation.getViolationMessage()));
  }

  @Test
  public void analyzeWeights() throws Exception {
    System.out.println(architectureAnalyser.analyzeWeights(architecture));
  }

}