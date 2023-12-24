package hr.fer.progi.looneycodes.BytePit.api.model;

// spring-boot imports
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Set;

import java.util.List;

/**
 * Entitet koji sprema podatke vezane uz zadatak i reference na testne primjere.
 */
@Entity
public class Zadatak {
  /**
   * id zadatka
   */
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="zadatakSeq")
  @SequenceGenerator(name="zadatakSeq", sequenceName = "zadatak_seq", initialValue=1001, allocationSize=1)
  private Integer zadatakId;
  /**
   * natjecanje u kojem se pojavljuje zadatak
   */
  @JsonIgnore
  @ManyToMany(mappedBy = "zadaci")
  private Set<Natjecanje> natjecanje;
  /**
   * autor/voditelj koji je napisao zadatak
   */
  @ManyToOne
  private Korisnik voditelj;
  /**
   * naziv zadatka
   */
  @Column(nullable = false)
  private String nazivZadatka;
  /**
   * broj bodova koje nosi zadatak
   */
  private int brojBodova;
  /**
   * vremensko ogranicenja za izvodenje test primjera
   * broj predstavlja broj sekundi izvodenja
   */
  private int vremenskoOgranicenje;
  /**
   * tekst zadatka
   */
  private String tekstZadatka;
  /**
   * je li zadatak privatni, tj. vidljiv samo voditelju koji ga je napisao
   */
  private boolean privatniZadatak;

  /**
   * tezina zadatka
   */
  @Enumerated(EnumType.STRING)
  private TezinaZadatka tezinaZadatka;

  @JsonIgnore
  @ManyToMany(mappedBy = "zadaci")
  List <VirtualnoNatjecanje> virtualnaNatjecanja;

  // geteri i seteri

  public Integer getZadatakId() {
    return zadatakId;
  }
  public Set<Natjecanje> getNatjecanje() {
    return natjecanje;
  }
  public void setNatjecanje(Set<Natjecanje> natjecanja) {
    this.natjecanje = natjecanja;
  }
  public Korisnik getVoditelj() {
    return voditelj;
  }
  public void setVoditelj(Korisnik voditelj) {
    this.voditelj = voditelj;
  }
  public String getNazivZadatka() {
    return nazivZadatka;
  }
  public void setNazivZadatka(String nazivZadatka) {
    this.nazivZadatka = nazivZadatka;
  }
  public int getBrojBodova() {
    return brojBodova;
  }
  public void setBrojBodova(int brojBodova) {
    if(brojBodova <= 0)
      throw new IllegalArgumentException();

    this.brojBodova = brojBodova;
  }
  public int getVremenskoOgranicenje() {
    return vremenskoOgranicenje;
  }
  public void setVremenskoOgranicenje(int vremenskoOgranicenje) {
    this.vremenskoOgranicenje = vremenskoOgranicenje;
  }
  public String getTekstZadatka() {
    return tekstZadatka;
  }
  public void setTekstZadatka(String tekstZadatka) {
    this.tekstZadatka = tekstZadatka;
  }
  public boolean isPrivatniZadatak() {
    return privatniZadatak;
  }
  public void setPrivatniZadatak(boolean privatniZadatak) {
    this.privatniZadatak = privatniZadatak;
  }
  public TezinaZadatka getTezinaZadatka() { return tezinaZadatka; }
  public void setTezinaZadatka(TezinaZadatka tezinaZadatka) { this.tezinaZadatka = tezinaZadatka; }
}
