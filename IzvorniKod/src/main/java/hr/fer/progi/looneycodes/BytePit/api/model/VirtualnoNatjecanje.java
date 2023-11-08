package hr.fer.progi.looneycodes.BytePit.api.model;

// spring-boot imports
import jakarta.persistence.*;

// java imports
import java.sql.Timestamp;

/**
 * Entitet koji definira instancu vritualnog natjecanja
 * Referencira se na "parent natjecanje"
 * TODO: povezivanje s bazom, testiranje
 */
@Entity
public class VirtualnoNatjecanje {
  /**
   * id virtualnog natjecanja u bazi
   */
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="virtualnoSeq")
  @SequenceGenerator(name="virtualnoSeq", sequenceName = "virtualno_natjecanje_seq", initialValue=100001, allocationSize=1)
  private Integer virtualnoNatjecanjeId;
  /**
   * natjecanje od kojeg je nastalo virtualno
   */
  @ManyToOne
  private Natjecanje orginalnoNatjecanje;
  /**
   * natjecatelj koji je generirao virtualno natjecanje
   */
  @ManyToOne
  private Korisnik natjecatelj;
  /**
   * vrijeme pocetka virtualnog natjecanja
   * read-only, generira se kad natjecatelj zatrazi novo virtualno natjecanje
   */
  private Timestamp vrijemePocetka;

  // geteri i seteri
  public Integer getVirtualnoNatjecanjeId() {
    return virtualnoNatjecanjeId;
  }
  public Natjecanje getOrginalnoNatjecanje() {
    return orginalnoNatjecanje;
  }
  public void setOrginalnoNatjecanje(Natjecanje orginalnoNatjecanje) {
    this.orginalnoNatjecanje = orginalnoNatjecanje;
  }
  public Korisnik getNatjecatelj() {
    return natjecatelj;
  }
  public void setNatjecatelj(Korisnik natjecatelj) {
    this.natjecatelj = natjecatelj;
  }
  public Timestamp getVrijemePocetka() {
    return vrijemePocetka;
  }
}
