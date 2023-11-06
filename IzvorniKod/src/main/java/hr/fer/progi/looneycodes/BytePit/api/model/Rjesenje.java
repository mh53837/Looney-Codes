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
   * TODO: dodaj partially unique
   */
  private int rjesenjeRb;
  /**
   * id natjecatelja koji je predao rjesenje
   * isti kao id korisnika s tim accountom
   */
  @ManyToOne
  private int natjecateljId;
  /**
   * id zadatka za koji je predano rjesenje
   */
  @ManyToOne
  private int zadatakId;
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
  public int getNatjecateljId() {
    return natjecateljId;
  }
  public void setNatjecateljId(int natjecateljId) {
    this.natjecateljId = natjecateljId;
  }
  public int getZadatakId() {
    return zadatakId;
  }
  public void setZadatakId(int zadatakId) {
    this.zadatakId = zadatakId;
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
