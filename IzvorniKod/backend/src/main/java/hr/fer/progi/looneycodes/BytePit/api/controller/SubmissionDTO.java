package hr.fer.progi.looneycodes.BytePit.api.controller;

public class SubmissionDTO {
  /**
   * identifikator nadmetanja
   */
  private Integer nadmetanjeId;
	/**
	 * identifikator zadatka
	 */
	private Integer zadatakId;
	 /**
	  * korisnicko ime natjecatelja koji je predao zadatka
	  */
	private String korisnickoIme;
	 /**
	  * tekst programskog koda
	  */
	private String programskiKod;
	
	//getteri i setteri
	public Integer getNadmetanjeId() {
	return nadmetanjeId;
  }
  public void setNadmetanjeId(Integer nadmetanjeId) {
    this.nadmetanjeId = nadmetanjeId;
  }
	public Integer getZadatakId() {
		return zadatakId;
	}
	public void setZadatakId(Integer zadatakId) {
		this.zadatakId = zadatakId;
	}
	public String getKorisnickoIme() {
		return korisnickoIme;
	}
	public void setKorisnickoIme(String korisnickoIme) {
		this.korisnickoIme = korisnickoIme;
	}
	public String getProgramskiKod() {
		return programskiKod;
	}
	public void setProgramskiKod(String programskiKod) {
		this.programskiKod = programskiKod;
	}
}
