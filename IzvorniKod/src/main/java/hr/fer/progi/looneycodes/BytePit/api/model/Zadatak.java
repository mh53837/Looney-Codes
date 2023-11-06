package hr.fer.progi.looneycodes.BytePit.api.model;

// spring-boot imports
import jakarta.persistence.*;

/**
 * Entitet koji sprema podatke vezane uz zadatak i reference na testne primjere.
 */
@Entity
public class Zadatak {
  /**
   * id zadatka
   */
  @Id
  @GeneratedValue
  private int zadatakId;
  /**
   * id natjecanja u kojem se pojavljuje zadatak
   */
  @ManyToOne
  private int natjecanjeId;
  /**
   * id autora/voditelja koji je napisao zadatak
   */
  @ManyToOne
  private int voditeljId;
  /**
   * naziv zadatka
   */
  @Column(nullable = false)
  private String nazivZadatka;
  /**
   * broj bodova koje nosi zadatak
   */
  private int brojBodova;
  /**
   * vremensko ogranicenja za izvodenje test primjera
   * broj predstavlja broj sekundi izvodenja
   */
  private int vremenskoOgranicenje;
  /**
   * tekst zadatka
   */
  private String tekstZadatka;
  /**
   * je li zadatak privatni, tj. vidljiv samo voditelju koji ga je napisao
   */
  private boolean privatniZadatak;

  // geteri i seteri

  public int getZadatakId() {
    return zadatakId;
  }
  public int getNatjecanjeId() {
    return natjecanjeId;
  }
  public void setNatjecanjeId(int natjecanjeId) {
    this.natjecanjeId = natjecanjeId;
  }
  public int getVoditeljId() {
    return voditeljId;
  }
  public void setVoditeljId(int voditeljId) {
    this.voditeljId = voditeljId;
  }
  public String getNazivZadatka() {
    return nazivZadatka;
  }
  public void setNazivZadatka(String nazivZadatka) {
    this.nazivZadatka = nazivZadatka;
  }
  public int getBrojBodova() {
    return brojBodova;
  }
  public void setBrojBodova(int brojBodova) {
    if(brojBodova <= 0)
      throw new IllegalArgumentException();

    this.brojBodova = brojBodova;
  }
  public int getVremenskoOgranicenje() {
    return vremenskoOgranicenje;
  }
  public void setVremenskoOgranicenje(int vremenskoOgranicenje) {
    this.vremenskoOgranicenje = vremenskoOgranicenje;
  }
  public String getTekstZadatka() {
    return tekstZadatka;
  }
  public void setTekstZadatka(String tekstZadatka) {
    this.tekstZadatka = tekstZadatka;
  }
  public boolean isPrivatniZadatak() {
    return privatniZadatak;
  }
  public void setPrivatniZadatak(boolean privatniZadatak) {
    this.privatniZadatak = privatniZadatak;
  }
}
