package hr.fer.progi.looneycodes.BytePit.api.model;

// spring-boot imports
import hr.fer.progi.looneycodes.BytePit.api.controller.AddPeharDTO;
import hr.fer.progi.looneycodes.BytePit.api.controller.NatjecanjeController;
import hr.fer.progi.looneycodes.BytePit.api.controller.RegisterKorisnikDTO;
import hr.fer.progi.looneycodes.BytePit.service.KorisnikService;
import hr.fer.progi.looneycodes.BytePit.service.NatjecanjeService;
import jakarta.persistence.*;

import java.util.Optional;

/**
 * Entitet u kojem spremamo pehare, mjesta, natjecanja i natjecatelje koji su ih osvojili.
 */
@Entity
public class Pehar {
  /**
   * defaultni konstruktor
   */
  public Pehar(){
  }
  /**
   * konstruktor koji se koristi kod registracije
   */
  public Pehar(Korisnik natjecatelj, Natjecanje natjecanje, int mjesto, String slikaPehara) {
    this.peharId = null;
    this.natjecatelj = natjecatelj;
    this.natjecanje = natjecanje;
    this.mjesto = mjesto;
    this.slikaPehara = slikaPehara;
  }

  /**
   * id pehara
   */
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="peharSeq")
  @SequenceGenerator(name="peharSeq", sequenceName = "pehar_seq", initialValue=1001, allocationSize=10)
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
  private String slikaPehara;

  // geteri i seteri

  public Integer getPeharId() {
    return peharId;
  }

  public void setPeharId(Integer peharId) {
    this.peharId = peharId;
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
    this.mjesto = mjesto;
  }

  public String getSlikaPehara() {
    return slikaPehara;
  }

  public void setSlikaPehara(String slikaPehara) {
    this.slikaPehara = slikaPehara;
  }
}
