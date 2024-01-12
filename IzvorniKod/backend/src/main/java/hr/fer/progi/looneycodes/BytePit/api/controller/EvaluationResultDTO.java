package hr.fer.progi.looneycodes.BytePit.api.controller;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * Klasa u kojoj imamo rezultat evaluacije: popis testnih primjera/prolaznosti te compiler output u slucaju greske
 */
public class EvaluationResultDTO {
  /**
   * statusi testnih primjera (https://ce.judge0.com/#statuses-and-languages-status-get)
   *
   * tldr:
   * 3 - prolazi,
   * 4 - greska u ispisu
   * 5 - time limit exceeded
   * 6 - compilation error
   */
  private List<Integer> tests;

  /**
   * polje koje sadrzi ispis kompajlera u slucaju da je status == 6 za neki primjer
   */
  private Optional<String> compilerOutput;
  /**
   * postotak tocnih odgovora
   */
  private double rezultat;

  public EvaluationResultDTO() {
    this.compilerOutput = Optional.empty();
    this.tests = new LinkedList<>();
  }

  public List<Integer> getTests() {
    return tests;
  }

  public void addStatus(int status) {
    this.tests.add(status);
  }

  public void setTests(List<Integer> tests) {
    this.tests = tests;
  }

  public Optional<String> getCompilerOutput() {
    return compilerOutput;
  }

  public void setCompilerOutput(Optional<String> compilerOutput) {
    this.compilerOutput = compilerOutput;
  }

  public double getRezultat() {
    return rezultat;
  }

  public void setRezultat(double rezultat) {
    if(rezultat < 0 || rezultat > 1)
      throw new IllegalArgumentException();

    this.rezultat = rezultat;
  }
}
