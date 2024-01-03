package hr.fer.progi.looneycodes.BytePit.api.model;

// spring-boot imports
import jakarta.persistence.*;

// java imports
import java.nio.file.Path;
import java.sql.Timestamp;
import java.util.Arrays;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;

import hr.fer.progi.looneycodes.BytePit.api.controller.RegisterKorisnikDTO;

/**
 * Entitet koji definira naseg korisnika, onako kako je u bazi definiran.
 */
@Entity
public class Korisnik{
  /**
   * id korisnika u bazi
   */
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="korisnikSeq")
  @SequenceGenerator(name="korisnikSeq", sequenceName = "korisnik_seq", initialValue=1, allocationSize=1)
  private Integer korisnikId;

  /**
   * jedinstveno korisnicko ime
   */
  @Column(unique = true, nullable = false)
  private String korisnickoIme;

  /**
   * hash vrijednost korisnikove lozinke
   */
  private String lozinka;
  /**
   * ime korisnika
   */
  private String ime;
  /**
   * prezime korisnika
   */
  private String prezime;
  /**
   * (potvrdena!) email adresa korisnika
   */
  @Column(unique = true, nullable = false)
  private String email;
  /**
   * bool koji se postavlja nakon potvrdivanja maila
   */
  private boolean confirmedEmail;
/**
   * profilna slika korisnika
   */
  private String fotografija;
  /**
   * vrijeme registracije, read-only (samo geter postoji)
   */
  @GeneratedValue
  private Timestamp vrijemeRegistracije;
  /**
   * uloga korisnika (admin, voditelj ili natjecatelj)
   */
  @Enumerated(EnumType.STRING)
  private Uloga uloga;
  /**
   * uloga koju korisnik trazi prilikom registracije
   * bitno za potencijalne voditelje!
   */
  @Enumerated(EnumType.STRING)
  private Uloga requestedUloga;

  /**
   * defaultni konstruktor
   */
  public Korisnik(){
  }
  /**
   * konstruktor koji se koristi kod registracije
   */
  public Korisnik(RegisterKorisnikDTO dto){
    this.korisnikId = null;
    this.korisnickoIme = dto.getKorisnickoIme();
    this.ime = dto.getIme();
    this.prezime = dto.getPrezime();
    this.lozinka = dto.getLozinka();
    this.email = dto.getEmail();
    this.requestedUloga = dto.getRequestedUloga();
    this.fotografija = dto.getFotografija();
  }
  /**
   * konstruktor koji se koristi kod azuriranja
   */
  public static Korisnik update(Korisnik stariKorisnik, RegisterKorisnikDTO dto){   
    Iterable<String> properties = Arrays.asList("ime", "prezime", "lozinka", "email", "fotografija", "requestedUloga");
    copyIfSpecified(dto, stariKorisnik, properties);
    return stariKorisnik;
  }
  /**
   * toString metoda koja se koristi uglavnom za debugiranje
   */
  @Override
  public String toString(){
    return "Korisnik: " + this.korisnickoIme +
            ", email: " + this.email +
            ", registriran: " + this.vrijemeRegistracije;
  }

  // geteri i seteri
  public Uloga getUloga() {
    return uloga;
  }
  public void setUloga(Uloga uloga) {
    this.uloga = uloga;
  }
  public Uloga getRequestedUloga() {
    return requestedUloga;
  }
  public void setRequestedUloga(Uloga requestedUloga) {
    this.requestedUloga = requestedUloga;
  }
  public Integer getKorisnikId() {
    return korisnikId;
  }
  public String getKorisnickoIme() {
    return korisnickoIme;
  }
  public void setKorisnickoIme(String korisnickoIme) {
    this.korisnickoIme = korisnickoIme;
  }
  public String getLozinka() {
    return lozinka;
  }
  public void setLozinka(String lozinka) {
    this.lozinka = lozinka;
  }
  public String getIme() {
    return ime;
  }
  public void setIme(String ime) {
    this.ime = ime;
  }
  public String getPrezime() {
    return prezime;
  }
  public void setPrezime(String prezime) {
    this.prezime = prezime;
  }
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }
  public String getFotografija() {
    return fotografija;
  }
  public void setFotografija(String fotografija) {
    this.fotografija = fotografija;
  }
  public Timestamp getVrijemeRegistracije() {
    return vrijemeRegistracije;
  }
  public void setVrijemeRegistracije(Timestamp vrijemeRegistracije){
    this.vrijemeRegistracije = vrijemeRegistracije;
  }
  public boolean isConfirmedEmail() {
	return confirmedEmail;
  }
  public void setConfirmedEmail(boolean confirmedEmail) {
    this.confirmedEmail = confirmedEmail;
  }


  private static void copyIfSpecified(RegisterKorisnikDTO dto, Korisnik korisnik, Iterable<String> props) {
	  BeanWrapper from = PropertyAccessorFactory.forBeanPropertyAccess(dto);
	  BeanWrapper to = PropertyAccessorFactory.forBeanPropertyAccess(korisnik);
	  props.forEach(p -> to.setPropertyValue(p, from.getPropertyValue(p) != null ? from.getPropertyValue(p) : to.getPropertyValue(p)));
  }
}
