package de.thb.fz.analyzer;

import de.thb.example.ArchitectureExample;
import de.thb.fz.dependency.DependencyLoader;
import de.thb.fz.dsl.Architecture;
import de.thb.fz.dsl.builder.ArchitectureBuilder;
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
    architectureAnalyser.analyzeInterfaceAndImplementation(architecture);
  }

  @Test
  public void analyzeConnection() throws Exception {
    architectureAnalyser.analyzeConnection(architecture);
  }

  @Test
  public void analyzeStyle() throws Exception {
    architectureAnalyser.analyzeStyle(architecture);
  }

  @Test
  public void analyzeWeights() throws Exception {
    architectureAnalyser.analyzeWeights(architecture);
  }

}