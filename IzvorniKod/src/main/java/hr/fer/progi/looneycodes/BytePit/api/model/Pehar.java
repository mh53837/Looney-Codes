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
  private int peharId;
  /**
   * id natjecatelja koji su osvojili
   */
  @ManyToOne
  private int natjecateljId;
  /**
   * id natjecanja na kojem je dodjeljen
   */
  @ManyToOne
  private int natjecanjeId;
  /**
   * mjesto za koje je dodijeljen (1, 2, 3 su validna mesta)
   */
  private int mjesto;
  /**
   * slika pehara
   */
  private byte[] slikaPehara;

  // geteri i seteri
  public int getPeharId() {
    return peharId;
  }
  public int getNatjecateljId() {
    return natjecateljId;
  }
  public void setNatjecateljId(int natjecateljId) {
    this.natjecateljId = natjecateljId;
  }
  public int getNatjecanjeId() {
    return natjecanjeId;
  }
  public void setNatjecanjeId(int natjecanjeId) {
    this.natjecanjeId = natjecanjeId;
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
