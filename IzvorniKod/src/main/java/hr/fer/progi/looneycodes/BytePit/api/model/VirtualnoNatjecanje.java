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
public class VirtualnoNatjecanje {
  /**
   * id virtualnog natjecanja u bazi
   */
  @Id
  @GeneratedValue
  private int virtualnoNatjecanjeId;
  /**
   * id natjecanja od kojeg je nastalo virtualno
   */
  @ManyToOne
  private int orginalnoNatjecanjeId;
  /**
   * id natjecatelja koji je generirao virtualno natjecanje
   */
  @OneToOne
  private int natjecateljId;
  /**
   * vrijeme pocetka virtualnog natjecanja
   * read-only, generira se kad natjecatelj zatrazi novo virtualno natjecanje
   */
  private Timestamp vrijemePocetka;

  // geteri i seteri
  public int getVirtualnoNatjecanjeId() {
    return virtualnoNatjecanjeId;
  }
  public int getOrginalnoNatjecanjeId() {
    return orginalnoNatjecanjeId;
  }
  public void setOrginalnoNatjecanjeId(int orginalnoNatjecanjeId) {
    this.orginalnoNatjecanjeId = orginalnoNatjecanjeId;
  }
  public int getNatjecateljId() {
    return natjecateljId;
  }
  public void setNatjecateljId(int natjecateljId) {
    this.natjecateljId = natjecateljId;
  }
  public Timestamp getVrijemePocetka() {
    return vrijemePocetka;
  }
}
