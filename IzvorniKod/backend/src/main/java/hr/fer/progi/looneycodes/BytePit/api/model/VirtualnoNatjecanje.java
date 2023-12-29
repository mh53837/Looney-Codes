package hr.fer.progi.looneycodes.BytePit.api.model;

// spring-boot imports
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

// java imports
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

/**
 * Entitet koji definira instancu vritualnog natjecanja
 * Referencira se na "parent natjecanje"
 * TODO: povezivanje s bazom, testiranje
 */
@Entity
public class VirtualnoNatjecanje extends Nadmetanje {

  public VirtualnoNatjecanje() {
  }
  public VirtualnoNatjecanje(Natjecanje orginalnoNatjecanje, Korisnik natjecatelj, Timestamp vrijemePocetka) {
	super(natjecatelj, vrijemePocetka);
	this.orginalnoNatjecanje = orginalnoNatjecanje;
  }

  @ManyToOne
  private Natjecanje orginalnoNatjecanje;
  /**
   * natjecatelj koji je generirao virtualno natjecanje
   */
  public Natjecanje getOrginalnoNatjecanje() {
    return orginalnoNatjecanje;
  }
  public void setOrginalnoNatjecanje(Natjecanje orginalnoNatjecanje) {
    this.orginalnoNatjecanje = orginalnoNatjecanje;
  }
  public Korisnik getNatjecatelj() {
    return getKorisnik();
  }
  public void setNatjecatelj(Korisnik natjecatelj) {
    setKorisnik(natjecatelj);
  }
  public Timestamp getVrijemePocetka() {
    return getPocetakNatjecanja();
  }
  public Integer getVirtualnoNatjecanjeId() {
	return getNatjecanjeId();
  }
  public Set<Zadatak> getListaZadataka() {
	return getZadaci();
  }
  
}
