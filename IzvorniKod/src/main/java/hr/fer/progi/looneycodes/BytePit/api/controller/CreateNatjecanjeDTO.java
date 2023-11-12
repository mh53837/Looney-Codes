package hr.fer.progi.looneycodes.BytePit.api.controller;

import java.sql.Timestamp;

public class CreateNatjecanjeDTO {
    private Integer natjecanjeId;
    private String nazivNatjecanja;
    private Timestamp pocetakNatjecanja;
    private Timestamp krajNatjecanja;
    private Integer voditeljId;

    public CreateNatjecanjeDTO() {
    }

    public CreateNatjecanjeDTO(Integer natjecanjeId, String nazivNatjecanja, Timestamp pocetakNatjecanja, Timestamp krajNatjecanja, Integer voditeljId) {
        this.natjecanjeId = natjecanjeId;
        this.nazivNatjecanja = nazivNatjecanja;
        this.pocetakNatjecanja = pocetakNatjecanja;
        this.krajNatjecanja = krajNatjecanja;
        this.voditeljId = voditeljId;
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

    public Integer getVoditeljId() {
        return voditeljId;
    }

    public void setVoditeljId(Integer voditeljId) {
        this.voditeljId = voditeljId;
    }
}
