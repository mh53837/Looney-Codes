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
   * udio tocnih primjera koje je postiglo trenutno rjesenje
   * read-only, evaluator ga postavlja jednom
   */
  private double brojTocnihPrimjera;
  /**
   * tekst programskog koda je bio evaluiran
   */
  @Column(columnDefinition = "text")
  private String programskiKod;
  /**
   * povezano nadmetanje
   */
  @ManyToOne
  private Nadmetanje natjecanje;

  /**
   * Defaultni Konstruktor (da nebi sql tulil)
   */
  public Rjesenje() {
  }
  /**
   * Konstruktor za stvaranje rjesenja
   */
  public Rjesenje(RjesenjeKey rjesenjeId, Timestamp vrijemeOdgovora, double brojTocnihPrimjera, String programskiKod, Nadmetanje natjecanje) {
    this.rjesenjeId = rjesenjeId;
    this.vrijemeOdgovora = vrijemeOdgovora;
    this.brojTocnihPrimjera = brojTocnihPrimjera;
    this.programskiKod = programskiKod;
    this.natjecanje = natjecanje;
  }

  /**
   * Konstruktor za stvaranje rjesenja povezanog s (virtualnim) natjecanjem
   */
//  public Rjesenje(RjesenjeKey rjesenjeId, Timestamp vrijemeOdgovora, double brojTocnihPrimjera, String programskiKod, Nadmetanje nadmetanje) {
//    this.rjesenjeId = rjesenjeId;
//    this.vrijemeOdgovora = vrijemeOdgovora;
//    this.brojTocnihPrimjera = brojTocnihPrimjera;
//    this.programskiKod = programskiKod;
//  }
  // geteri i seteri
  public RjesenjeKey getRjesenjeId() { return rjesenjeId; }
  public void setRjesenjeId(RjesenjeKey rjesenjeId) { this.rjesenjeId = rjesenjeId; }
  public Timestamp getVrijemeOdgovora() {
    return vrijemeOdgovora;
  }
  public double getBrojTocnihPrimjera() {
    return brojTocnihPrimjera;
  }
  public String getProgramskiKod() {
    return programskiKod;
  }
  public Nadmetanje getNatjecanje() {
	return natjecanje;
  }
  public void setNatjecanje(Nadmetanje natjecanje) {
	this.natjecanje = natjecanje;
  }
}

