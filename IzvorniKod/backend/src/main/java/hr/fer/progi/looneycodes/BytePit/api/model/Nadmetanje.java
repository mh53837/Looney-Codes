package hr.fer.progi.looneycodes.BytePit.api.model;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Nadmetanje {
  /**
   * id natjecanja u bazi
   */
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="natjecanjeSeq")
  @SequenceGenerator(name="natjecanjeSeq", sequenceName = "natjecanje_seq", initialValue=101, allocationSize=1)
  private Integer natjecanjeId;
  /**
   * voditelj natjecanja
   */
  @ManyToOne
  private Korisnik korisnik;

  /**
   * vrijeme pocetka natjecanja
   */
  private Timestamp pocetakNatjecanja;
  
  @JsonIgnore
  @ManyToMany(fetch = FetchType.EAGER)
  private Set<Zadatak> zadaci;

  public Nadmetanje() {
  }
  public Nadmetanje(Korisnik korisnik, Timestamp pocetakNatjecanja) {
      this.korisnik = korisnik;
      this.pocetakNatjecanja = pocetakNatjecanja;
  }
  
  
  // geteri i seteri

  public Set<Zadatak> getZadaci() { return zadaci; }
  public void setZadaci(Set<Zadatak> zadaci) { this.zadaci = zadaci; }
  public Integer getNatjecanjeId() {
    return natjecanjeId;
  }
  public Korisnik getKorisnik() {
	return korisnik;
  }
  public void setKorisnik(Korisnik korisnik) {
    this.korisnik = korisnik;
  }
  public Timestamp getPocetakNatjecanja() {
    return pocetakNatjecanja;
  }
  public void setPocetakNatjecanja(Timestamp pocetakNatjecanja) {
       this.pocetakNatjecanja = pocetakNatjecanja;
  }

  public void setListaZadataka(List<Zadatak> set) {
		zadaci = Set.of(set.toArray(new Zadatak[set.size()]));
  }
}
