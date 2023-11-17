package hr.fer.progi.looneycodes.BytePit.api.controller;

// local imports
import hr.fer.progi.looneycodes.BytePit.api.model.Uloga;

/**
 * Objekt koji se preda routeru prilikom registracije
 */
public class RegisterKorisnikDTO {
  /**
   * username korisnika kojeg registriramo
   */
  private String korisnickoIme;
  /**
   * ime korisnika
   */
  private String ime;
  /**
   * prezime korisnika
   */
  private String prezime;
/**
   * password kojeg registriramo (ne hashirani, sustav ga hashira prije spremanja)
   */
  private String lozinka;
  /**
   * email kojeg registriramo
   */
  private String email;
  /**
   * zatrazena uloga novog korisnika
   */
  private Uloga requestedUloga;
  /**
   * profilna slika
   */
  private String fotografija;

// geteri i seteri
  public String getFotografija() {
    return fotografija;
  }
  public void setFotografija(String fotografija) {
    this.fotografija = fotografija;
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
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }
  public Uloga getRequestedUloga() {
    return requestedUloga;
  }
  public void setRequestedUloga(Uloga requestedUloga) {
    this.requestedUloga = requestedUloga;
  }
}
