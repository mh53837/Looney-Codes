package hr.fer.progi.looneycodes.BytePit.api.model;

// spring-boot imports
import jakarta.persistence.*;

// java imports
import java.sql.Timestamp;

/**
 * Entitet koji definira natjecanje, identifikator, vrijeme pocetka i kraja
 * TODO: povezivanje s bazom, testiranje
 */
@Entity
public class Natjecanje {
  /**
   * id natjecanja u bazi
   */
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="natjecanjeSeq")
  @SequenceGenerator(name="natjecanjeSeq", sequenceName = "natjecanje_seq", initialValue=101, allocationSize=1)
  private Integer natjecanjeId;
  /**
   * voditelj natjecanja
   */
  @ManyToOne
  private Korisnik voditelj;
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
  public Integer getNatjecanjeId() {
    return natjecanjeId;
  }
  public Korisnik getVoditelj() {
    return voditelj;
  }
  public void setVoditelj(Korisnik voditelj) {
    this.voditelj = voditelj;
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
