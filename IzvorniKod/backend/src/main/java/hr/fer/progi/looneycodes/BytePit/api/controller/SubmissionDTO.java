package hr.fer.progi.looneycodes.BytePit.api.controller;

import java.sql.Timestamp;

import hr.fer.progi.looneycodes.BytePit.api.model.RjesenjeKey;
import jakarta.persistence.EmbeddedId;

public class SubmissionDTO {
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
	/**
	 * 
	 * @return
	 */
	private String stdin;
	/**
	 * 
	 * @return
	 */
	private String expectedOutput;
	
	
	//getteri i setteri
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
