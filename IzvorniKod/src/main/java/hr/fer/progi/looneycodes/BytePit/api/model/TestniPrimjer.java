package hr.fer.progi.looneycodes.BytePit.api.model;

import java.util.Comparator;

// spring-boot imports
import jakarta.persistence.*;

/**
 * Entitet koji predstavlja testne primjere vezane uz neki zadatak
 */
@Entity 
public class TestniPrimjer{
  /**
   * redni broj testnog primjera
   */
  @EmbeddedId
  private TestniPrimjerKey testniPrimjerId;
  
/**
   * ulazni podaci programa koji se testira
   */
  private String ulazniPodaci;
  /**
   * izlazni podaci programa koji se testira
   */
  private String izlazniPodaci;

  // geteri i seteri
  public TestniPrimjerKey getTestniPrimjerId() {
	return testniPrimjerId;
  }
  public void setTestniPrimjerId(TestniPrimjerKey testniPrimjerId) {
	this.testniPrimjerId = testniPrimjerId;
  }
  public String getUlazniPodaci() {
    return ulazniPodaci;
  }
  public void setUlazniPodaci(String ulazniPodaci) {
    this.ulazniPodaci = ulazniPodaci;
  }
  public String getIzlazniPodaci() {
    return izlazniPodaci;
  }
  public void setIzlazniPodaci(String izlazniPodaci) {
    this.izlazniPodaci = izlazniPodaci;
  }

  
}
