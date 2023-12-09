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
   * identifikator rjesenja
   */
  @EmbeddedId
  private RjesenjeKey rjesenjeId;

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

  /**
   * Konstruktor za stvaranje rjesenja
   */
  public Rjesenje(RjesenjeKey rjesenjeId, Timestamp vrijemeOdgovora, int brojTocnihPrimjera, String programskiKod) {
    this.rjesenjeId = rjesenjeId;
    this.vrijemeOdgovora = vrijemeOdgovora;
    this.brojTocnihPrimjera = brojTocnihPrimjera;
    this.programskiKod = programskiKod;
  }

  // geteri i seteri
  public RjesenjeKey getRjesenjeId() { return rjesenjeId; }
  public void setRjesenjeId(RjesenjeKey rjesenjeId) { this.rjesenjeId = rjesenjeId; }
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

