package hr.fer.progi.looneycodes.BytePit.api.controller;

public class RjesenjeDTO {
	private Integer zadatakId;
	private String korisnickoIme;
	private Integer natjecanjeId;
	private Integer rBr;
	private double postotakTocnihPrimjera;
	
	
	
	public RjesenjeDTO(Integer zadatakId, String korisnickoIme, Integer natjecanjeId, Integer rBr,
			double postotakTocnihPrimjera) {
		super();
		this.zadatakId = zadatakId;
		this.korisnickoIme = korisnickoIme;
		this.natjecanjeId = natjecanjeId;
		this.rBr = rBr;
		this.postotakTocnihPrimjera = postotakTocnihPrimjera*100;
	}
	
	public RjesenjeDTO(Integer zadatakId, String korisnickoIme, Integer rBr,
			double postotakTocnihPrimjera) {
		this(zadatakId, korisnickoIme, null, rBr, postotakTocnihPrimjera);
	}
	
	public Integer getZadtakId() {
		return zadatakId;
	}
	public void setZadtakId(Integer zadtakId) {
		this.zadatakId = zadtakId;
	}
	public String getKorisnickoIme() {
		return korisnickoIme;
	}
	public void setKorisnickoIme(String korisnickoIme) {
		this.korisnickoIme = korisnickoIme;
	}
	public Integer getNatjecanjeId() {
		return natjecanjeId;
	}
	public void setNatjecanjeId(Integer natjecanjeId) {
		this.natjecanjeId = natjecanjeId;
	}
	public Integer getrBr() {
		return rBr;
	}
	public void setrBr(Integer rBr) {
		this.rBr = rBr;
	}
	public double getPostotakTocnihPrimjera() {
		return postotakTocnihPrimjera;
	}
	public void setPostotakTocnihPrimjera(double postotakTocnihPrimjera) {
		this.postotakTocnihPrimjera = postotakTocnihPrimjera;
	}
	
	

}
