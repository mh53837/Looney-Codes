package hr.fer.progi.looneycodes.BytePit.api.model;

// spring-boot imports
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

// java imports
import java.sql.Timestamp;
import java.util.List;

/**
 * Entitet koji definira instancu vritualnog natjecanja
 * Referencira se na "parent natjecanje"
 * TODO: povezivanje s bazom, testiranje
 */
@Entity
public class VirtualnoNatjecanje {

  public VirtualnoNatjecanje() {
  }
  public VirtualnoNatjecanje(Natjecanje orginalnoNatjecanje, Korisnik natjecatelj, Timestamp vrijemePocetka) {
    this.orginalnoNatjecanje = orginalnoNatjecanje;
    this.natjecatelj = natjecatelj;
    this.vrijemePocetka = vrijemePocetka;
  }

  /**
   * id virtualnog natjecanja u bazi
   */
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="virtualnoSeq")
  @SequenceGenerator(name="virtualnoSeq", sequenceName = "virtualno_natjecanje_seq", initialValue=100001, allocationSize=1)
  private Integer virtualnoNatjecanjeId;
  /**
   * natjecanje od kojeg je nastalo virtualno
   */
  @ManyToOne
  private Natjecanje orginalnoNatjecanje;
  /**
   * natjecatelj koji je generirao virtualno natjecanje
   */
  @ManyToOne
  private Korisnik natjecatelj;
  /**
   * vrijeme pocetka virtualnog natjecanja
   * read-only, generira se kad natjecatelj zatrazi novo virtualno natjecanje
   */
  private Timestamp vrijemePocetka;

  @JsonIgnore
  @ManyToMany
  private List<Zadatak> listaZadataka;

  // geteri i seteri
  public List<Zadatak> getListaZadataka() { return listaZadataka; }
  public void setListaZadataka(List<Zadatak> listaZadataka) { this.listaZadataka = listaZadataka; }
  public Integer getVirtualnoNatjecanjeId() {
    return virtualnoNatjecanjeId;
  }
  public Natjecanje getOrginalnoNatjecanje() {
    return orginalnoNatjecanje;
  }
  public void setOrginalnoNatjecanje(Natjecanje orginalnoNatjecanje) {
    this.orginalnoNatjecanje = orginalnoNatjecanje;
  }
  public Korisnik getNatjecatelj() {
    return natjecatelj;
  }
  public void setNatjecatelj(Korisnik natjecatelj) {
    this.natjecatelj = natjecatelj;
  }
  public Timestamp getVrijemePocetka() {
    return vrijemePocetka;
  }
}
