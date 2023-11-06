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
   * TODO: probaj dodati partially unique
   */
  private int testniPrimjerRb;
  /**
   * id zadatka
   */
  @ManyToOne
  private int zadatakId;
  /**
   * ulazni podaci programa koji se testira
   */
  private String ulazniPodaci;
  /**
   * izlazni podaci programa koji se testira
   */
  private String izlazniPodaci;

  // geteri i seteri
  public int getTestniPrimjerRb() {
    return testniPrimjerRb;
  }
  public void setTestniPrimjerRb(int testniPrimjerRb) {
    this.testniPrimjerRb = testniPrimjerRb;
  }
  public int getZadatakId() {
    return zadatakId;
  }
  public void setZadatakId(int zadatakId) {
    this.zadatakId = zadatakId;
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
