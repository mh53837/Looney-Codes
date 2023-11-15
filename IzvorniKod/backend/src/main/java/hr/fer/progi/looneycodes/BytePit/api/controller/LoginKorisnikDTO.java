package hr.fer.progi.looneycodes.BytePit.api.controller;

/**
 * Objekt koji se predaje tijekom logina
 */
public class LoginKorisnikDTO {
  /**
   * username koji se predaje prilikom logina
   */
  private String korisnickoIme;
  /**
   * lozinka koja se predaje prilikom logina
   */
  private String lozinka;

  // geteri i seteri
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
}
