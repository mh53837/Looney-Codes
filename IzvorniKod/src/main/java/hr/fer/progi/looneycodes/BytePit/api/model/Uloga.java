package hr.fer.progi.looneycodes.BytePit.api.model;

// spring-boot imports
import jakarta.persistence.*;

/**
 * Entitet koji odreduje ulogu korisnika.
 * validne uloge su: administrator, voditelj i natjecatelj
 */
@Entity
public class Uloga {
  /**
   * id uloge
   */
  @Id
  @GeneratedValue
  private int ulogaId;
  /**
   * naziv uloge
   */
  private String nazivUloge;

  // geteri i seteri

  public int getUlogaId() {
    return ulogaId;
  }
  public String getNazivUloge() {
    return nazivUloge;
  }
  public void setNazivUloge(String nazivUloge) {
    if(!nazivUloge.equals("ADMIN") &&
      !nazivUloge.equals("VODITELJ") &&
      !nazivUloge.equals("NATJECATELJ"))
      throw new IllegalArgumentException();

    this.nazivUloge = nazivUloge;
  }
}
