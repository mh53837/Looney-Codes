package hr.fer.progi.looneycodes.BytePit.api.controller;

// java imports
import java.sql.Timestamp;

// local imports
import hr.fer.progi.looneycodes.BytePit.api.model.Korisnik;
import hr.fer.progi.looneycodes.BytePit.api.model.Uloga;

/**
 * Objekt u kojem se salju javno dostupne informacije o korisniku
 * Na taj nacin ne saljemo lozinku bezveze na frontend
 */
public class KorisnikInfoDTO {
  /**
   * javno korisnicko ime
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
   * email korisnika
   */
  private String email;
  /**
   * trenutna uloga korisnika (zatrazena nije javna)
   */
  private Uloga uloga;
  /**
   * vrijeme registracije
   */
  private Timestamp vrijemeRegistracije;
  /**
   * profilna slika
   */
  private byte[] fotografija;

  /**
   * konstruktor koji stvara DTO prema zadanom korisniku
   * @see Korisnik
   */
  public KorisnikInfoDTO(Korisnik korisnik){
    this.korisnickoIme = korisnik.getKorisnickoIme();
    this.ime = korisnik.getIme();
    this.prezime = korisnik.getPrezime();
    this.email = korisnik.getEmail();
    this.uloga = korisnik.getUloga();
    this.vrijemeRegistracije = korisnik.getVrijemeRegistracije();
    this.fotografija = korisnik.getFotografija();
  }
  // geteri i seteri

  public byte[] getFotografija() {
    return fotografija;
  }
  public void setFotografija(byte[] fotografija) {
    this.fotografija = fotografija;
  }
  public String getKorisnickoIme() {
    return korisnickoIme;
  }
  public void setKorisnickoIme(String korisnickoIme) {
    this.korisnickoIme = korisnickoIme;
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
  public Uloga getUloga() {
    return uloga;
  }
  public void setUloga(Uloga uloga) {
    this.uloga = uloga;
  }
  public Timestamp getVrijemeRegistracije() {
    return vrijemeRegistracije;
  }
  public void setVrijemeRegistracije(Timestamp vrijemeRegistracije) {
    this.vrijemeRegistracije = vrijemeRegistracije;
  }
}
