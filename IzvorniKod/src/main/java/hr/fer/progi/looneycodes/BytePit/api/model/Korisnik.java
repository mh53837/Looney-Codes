package hr.fer.progi.looneycodes.BytePit.api.model;

// spring-boot imports
import jakarta.persistence.*;

// java imports
import java.sql.Timestamp;

/**
 * Entitet koji definira naseg korisnika, onako kako je u bazi definiran.
 * TODO: povezivanje s bazom, testiranje
 */
@Entity
public class Korisnik{
  /**
   * id korisnika u bazi
   */
  @Id
  @GeneratedValue
  private int korisnikId;

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
   * profilna slika korisnika
   */
  private byte[] fotografija;
  /**
   * vrijeme registracije, read-only (samo geter postoji)
   */
  private Timestamp vrijemeRegistracije;
  /**
   * uloga korisnika (admin, voditelj ili natjecatelj)
   */
  @ManyToOne
  private int ulogaId;

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
  public int getKorisnikId() {
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
}
