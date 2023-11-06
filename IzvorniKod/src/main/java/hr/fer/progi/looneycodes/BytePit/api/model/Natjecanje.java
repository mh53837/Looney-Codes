package hr.fer.progi.looneycodes.BytePit.api.model;

// spring-boot imports
import jakarta.persistence.*;

// java imports
import java.sql.Timestamp;

/**
 * Entitet koji definira natjecanje, identifikator, vrijeme pocetka i kraja
 * TODO: povezivanje s bazom, testiranje
 */
public class Natjecanje {
  /**
   * id natjecanja u bazi
   */
  @Id
  @GeneratedValue
  private int natjecanjeId;
  /**
   * id voditelja natjecanja
   */
  @ManyToOne
  @Column(nullable = false)
  private int voditeljId;
  /**
   * naziv natjecanja
   */
  @Column(nullable = false)
  private String nazivNatjecanja;
  /**
   * vrijeme pocetka natjecanja
   */
  private Timestamp pocetakNatjecanja;
  /**
   * vrijeme kraja natjecanja
   */
  private Timestamp krajNatjecanja;

  // geteri i seteri
  public int getNatjecanjeId() {
    return natjecanjeId;
  }
  public int getVoditeljId() {
    return voditeljId;
  }
  public void setVoditeljId(int voditeljId) {
    this.voditeljId = voditeljId;
  }
  public String getNazivNatjecanja() {
    return nazivNatjecanja;
  }
  public void setNazivNatjecanja(String nazivNatjecanja) {
    this.nazivNatjecanja = nazivNatjecanja;
  }
  public Timestamp getPocetakNatjecanja() {
    return pocetakNatjecanja;
  }
  public void setPocetakNatjecanja(Timestamp pocetakNatjecanja) {
    if(pocetakNatjecanja.compareTo(this.krajNatjecanja) >= 0)
      throw new IllegalArgumentException();

    this.pocetakNatjecanja = pocetakNatjecanja;
  }
  public Timestamp getKrajNatjecanja() {
    return krajNatjecanja;
  }
  public void setKrajNatjecanja(Timestamp krajNatjecanja) {
    if(krajNatjecanja.compareTo(this.pocetakNatjecanja) <= 0)
      throw new IllegalArgumentException();

    this.krajNatjecanja = krajNatjecanja;
  }
}
