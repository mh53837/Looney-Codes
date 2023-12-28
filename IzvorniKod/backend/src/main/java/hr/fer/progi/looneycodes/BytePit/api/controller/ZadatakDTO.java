package hr.fer.progi.looneycodes.BytePit.api.controller;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import hr.fer.progi.looneycodes.BytePit.api.model.Korisnik;
import hr.fer.progi.looneycodes.BytePit.api.model.Natjecanje;
import hr.fer.progi.looneycodes.BytePit.api.model.TezinaZadatka;
import hr.fer.progi.looneycodes.BytePit.api.model.Zadatak;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;

public class ZadatakDTO {
	  /**
	   * id zadatka
	   */
	  private Integer zadatakId;
	  /**
	   * autor/voditelj koji je napisao zadatak
	   */
	  private String voditelj;
	  /**
	   * naziv zadatka
	   */
	  private String nazivZadatka;
	  /**
	   * tezina zadatka
	   */
	  private TezinaZadatka tezinaZadatka;
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
	   * broj bodova koje nosi zadatak
	   */
	  private int brojBodova;
	  
	  public ZadatakDTO() {
		  super();
	  }
	  
	  public ZadatakDTO(Zadatak zadatak) {
		  this.nazivZadatka = zadatak.getNazivZadatka();
		  this.tezinaZadatka = zadatak.getTezinaZadatka();
		  this.privatniZadatak = zadatak.isPrivatniZadatak();
		  this.voditelj = zadatak.getVoditelj().getKorisnickoIme();
		  this.vremenskoOgranicenje = zadatak.getVremenskoOgranicenje();
		  this.zadatakId = zadatak.getZadatakId();
		  this.tekstZadatka = zadatak.getTekstZadatka();
		  setBrojBodova();
	  }

	public ZadatakDTO(Integer zadatakId, String voditelj, String nazivZadatka, TezinaZadatka tezinaZadatka,
			int vremenskoOgranicenje, String tekstZadatka, boolean privatniZadatak) {
		super();
		this.zadatakId = zadatakId;
		this.voditelj = voditelj;
		this.nazivZadatka = nazivZadatka;
		this.tezinaZadatka = tezinaZadatka;
		this.vremenskoOgranicenje = vremenskoOgranicenje;
		this.tekstZadatka = tekstZadatka;
		this.privatniZadatak = privatniZadatak;
		setBrojBodova();
	}
	
	public ZadatakDTO(String voditelj, String nazivZadatka, TezinaZadatka tezinaZadatka,
			int vremenskoOgranicenje, String tekstZadatka, boolean privatniZadatak) {
		super();
		this.voditelj = voditelj;
		this.nazivZadatka = nazivZadatka;
		this.tezinaZadatka = tezinaZadatka;
		this.vremenskoOgranicenje = vremenskoOgranicenje;
		this.tekstZadatka = tekstZadatka;
		this.privatniZadatak = privatniZadatak;
		setBrojBodova();
	}

	//geteri i seteri

	public Integer getZadatakId() {
		return zadatakId;
	}

	public void setZadatakId(Integer zadatakId) {
		this.zadatakId = zadatakId;
	}

	public String getVoditelj() {
		return voditelj;
	}

	public void setVoditelj(String voditelj) {
		this.voditelj = voditelj;
	}

	public String getNazivZadatka() {
		return nazivZadatka;
	}

	public void setNazivZadatka(String nazivZadatka) {
		this.nazivZadatka = nazivZadatka;
	}

	public TezinaZadatka getTezinaZadatka() {
		return tezinaZadatka;
	}

	public void setTezinaZadatka(TezinaZadatka tezinaZadatka) {
		this.tezinaZadatka = tezinaZadatka;
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
	  
	 public int getBrojBodova() {
		    return brojBodova;
		  }
  public void setBrojBodova() {
	  switch(this.tezinaZadatka) {
	  case RECRUIT:
		this.brojBodova = 10;
	    break;
	  case VETERAN:
		this.brojBodova = 20;
	    break;
	  case REALISM:
		this.brojBodova = 50;
	    break;
	  default:
	    this.brojBodova = 0;
	}
  }
	  
}
