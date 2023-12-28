package hr.fer.progi.looneycodes.BytePit.api.controller;

import hr.fer.progi.looneycodes.BytePit.api.model.Natjecanje;

import java.sql.Timestamp;

public class CreateNatjecanjeDTO {
    private Integer natjecanjeId;
    private String nazivNatjecanja;
    private Timestamp pocetakNatjecanja;
    private Timestamp krajNatjecanja;
    private String korisnickoImeVoditelja;

    public CreateNatjecanjeDTO() {
    }

    public CreateNatjecanjeDTO(Integer natjecanjeId, String nazivNatjecanja, Timestamp pocetakNatjecanja, Timestamp krajNatjecanja, String korisnickoImeVoditelja) {
        this.natjecanjeId = natjecanjeId;
        this.nazivNatjecanja = nazivNatjecanja;
        this.pocetakNatjecanja = pocetakNatjecanja;
        this.krajNatjecanja = krajNatjecanja;
        this.korisnickoImeVoditelja = korisnickoImeVoditelja;
    }
    public CreateNatjecanjeDTO(Natjecanje natjecanje){
        this.natjecanjeId = natjecanje.getNatjecanjeId();
        this.nazivNatjecanja = natjecanje.getNazivNatjecanja();
        this.pocetakNatjecanja = natjecanje.getPocetakNatjecanja();
        this.krajNatjecanja = natjecanje.getKrajNatjecanja();
        this.korisnickoImeVoditelja = natjecanje.getVoditelj().getKorisnickoIme();
    }

    public Integer getNatjecanjeId() {
        return natjecanjeId;
    }

    public void setNatjecanjeId(Integer natjecanjeId) {
        this.natjecanjeId = natjecanjeId;
    }

    public String getNazivNatjecanja() {
        return nazivNatjecanja;
    }

    public void setNazivNatjecanja(String nazivNatjecanja) {
        this.nazivNatjecanja = nazivNatjecanja;
    }

    public Timestamp getPocetakNatjecanja() {
        return pocetakNatjecanja;
    }

    public void setPocetakNatjecanja(Timestamp pocetakNatjecanja) {
        this.pocetakNatjecanja = pocetakNatjecanja;
    }

    public Timestamp getKrajNatjecanja() {
        return krajNatjecanja;
    }

    public void setKrajNatjecanja(Timestamp krajNatjecanja) {
        this.krajNatjecanja = krajNatjecanja;
    }

    public String getKorisnickoImeVoditelja() {
        return korisnickoImeVoditelja;
    }
    public void setKorisnickoImeVoditelja(String korisnickoImeVoditelja) {
        this.korisnickoImeVoditelja = korisnickoImeVoditelja;
    }

}
