package hr.fer.progi.looneycodes.BytePit.api.model;

// spring-boot imports
import jakarta.persistence.*;

/**
 * Entitet koji predstavlja testne primjere vezane uz neki zadatak
 */
@Entity
public class TestniPrimjer {
  /**
   * redni broj testnog primjera
   */
  @Id
  private Integer testniPrimjerRb;
  /**
   * zadatak koji testiramo
   */
  @Id
  @ManyToOne
  private Zadatak zadatak;
  /**
   * ulazni podaci programa koji se testira
   */
  private String ulazniPodaci;
  /**
   * izlazni podaci programa koji se testira
   */
  private String izlazniPodaci;

  // geteri i seteri
  public Integer getTestniPrimjerRb() {
    return testniPrimjerRb;
  }
  public void setTestniPrimjerRb(int testniPrimjerRb) {
    this.testniPrimjerRb = testniPrimjerRb;
  }
  public Zadatak getZadatak() {
    return zadatak;
  }
  public void setZadatak(Zadatak zadatak) {
    this.zadatak = zadatak;
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
