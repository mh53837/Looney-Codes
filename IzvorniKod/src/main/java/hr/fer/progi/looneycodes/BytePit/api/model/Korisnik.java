package hr.fer.progi.looneycodes.BytePit.api.model;

// spring-boot imports
import jakarta.persistence.*;

// java imports
import java.sql.Timestamp;

/**
 * Entitet koji definira naseg korisnika, onako kako je u bazi definiran.
 */
@Entity
public class Korisnik{
  /**
   * id korisnika u bazi
   */
  @Id
  @GeneratedValue
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
  private byte[] fotografija;
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
  public byte[] getFotografija() {
    return fotografija;
  }
  public void setFotografija(byte[] fotografija) {
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
}
