package hr.fer.progi.looneycodes.BytePit.api.model;

// spring-boot imports
import jakarta.persistence.*;

/**
 * Entitet u kojem spremamo pehare, mjesta, natjecanja i natjecatelje koji su ih osvojili.
 */
@Entity
public class Pehar {
  /**
   * id pehara
   */
  @Id
  @GeneratedValue
  private Integer peharId;
  /**
   * natjecatelj koji je osvojio
   */
  @ManyToOne
  private Korisnik natjecatelj;
  /**
   * natjecanje na kojem je dodjeljen
   */
  @ManyToOne
  private Natjecanje natjecanje;
  /**
   * mjesto za koje je dodijeljen (1, 2, 3 su validna mesta)
   */
  private int mjesto;
  /**
   * slika pehara
   */
  private byte[] slikaPehara;

  // geteri i seteri
  public Integer getPeharId() {
    return peharId;
  }
  public Korisnik getNatjecatelj() {
    return natjecatelj;
  }
  public void setNatjecatelj(Korisnik natjecatelj) {
    this.natjecatelj = natjecatelj;
  }
  public Natjecanje getNatjecanje() {
    return natjecanje;
  }
  public void setNatjecanje(Natjecanje natjecanje) {
    this.natjecanje = natjecanje;
  }
  public int getMjesto() {
    return mjesto;
  }
  public void setMjesto(int mjesto) {
    if(mjesto < 1 || mjesto > 3)
      throw new IllegalArgumentException();

    this.mjesto = mjesto;
  }
  public byte[] getSlikaPehara() {
    return slikaPehara;
  }
  public void setSlikaPehara(byte[] slikaPehara) {
    this.slikaPehara = slikaPehara;
  }
}
