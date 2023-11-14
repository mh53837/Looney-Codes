package hr.fer.progi.looneycodes.BytePit.api.model;

// spring-boot imports
import jakarta.persistence.*;

// java imports
import java.sql.Timestamp;

/**
 * entitet koji definira rjesenje koje je odredeni natjecatelj predao za odredeni zadatak
 */
@Entity
public class Rjesenje {
  /**
   * redni broj predanog rjesenja
   */
  @Id
  private int rjesenjeRb;
  /**
   * natjecatelj koji je predao rjesenje
   */
  @Id
  @ManyToOne
  private Korisnik natjecatelj;
  /**
   * zadatak za koji je predano rjesenje
   */
  @Id
  @ManyToOne
  private Zadatak zadatak;
  /**
   * vrijeme kad je poslano rjesenje
   * read-only, generira se u trenutku kad se salje
   */
  private Timestamp vrijemeOdgovora;
  /**
   * broj tocnih primjera koje je postiglo trenutno rjesenje
   * read-only, evaluator ga postavlja jednom
   */
  private int brojTocnihPrimjera;
  /**
   * tekst programskog koda je bio evaluiran
   */
  private String programskiKod;

  // geteri i seteri
  public int getRjesenjeRb() {
    return rjesenjeRb;
  }
  public Korisnik getNatjecatelj() {
    return natjecatelj;
  }
  public void setNatjecatelj(Korisnik natjecatelj) {
    this.natjecatelj = natjecatelj;
  }
  public Zadatak getZadatak() {
    return zadatak;
  }
  public void setZadatak(Zadatak zadatak) {
    this.zadatak = zadatak;
  }
  public Timestamp getVrijemeOdgovora() {
    return vrijemeOdgovora;
  }
  public int getBrojTocnihPrimjera() {
    return brojTocnihPrimjera;
  }
  public String getProgramskiKod() {
    return programskiKod;
  }
}
