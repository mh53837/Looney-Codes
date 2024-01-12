package hr.fer.progi.looneycodes.BytePit.api.controller;

import hr.fer.progi.looneycodes.BytePit.api.model.Natjecanje;
import hr.fer.progi.looneycodes.BytePit.api.model.VirtualnoNatjecanje;

import java.sql.Timestamp;

public class VirtualnoNatjecanjeDTO {
    private Integer virtualnoNatjecanjeId;
    private Integer orginalnoNatjecanjeId;
    private String korisnickoImeNatjecatelja;
    Timestamp vrijemePocetka;

    public VirtualnoNatjecanjeDTO() {
    }
    public VirtualnoNatjecanjeDTO(Integer virtualnoNatjecanjeId, Integer orginalnoNatjecanjeId, String korisnickoImeNatjecatelja, Timestamp vrijemePocetka) {
        this.virtualnoNatjecanjeId = virtualnoNatjecanjeId;
        this.orginalnoNatjecanjeId = orginalnoNatjecanjeId;
        this.korisnickoImeNatjecatelja = korisnickoImeNatjecatelja;
        this.vrijemePocetka = vrijemePocetka;
    }

    public VirtualnoNatjecanjeDTO(VirtualnoNatjecanje virtualnoNatjecanje){
        this.virtualnoNatjecanjeId = virtualnoNatjecanje.getVirtualnoNatjecanjeId();
        Natjecanje origNatjecanje = virtualnoNatjecanje.getOrginalnoNatjecanje();
        this.orginalnoNatjecanjeId =  origNatjecanje == null ? null : origNatjecanje.getNatjecanjeId();
        this.korisnickoImeNatjecatelja = virtualnoNatjecanje.getNatjecatelj().getKorisnickoIme();
        this.vrijemePocetka = virtualnoNatjecanje.getVrijemePocetka();
    }

    public Integer getVirtualnoNatjecanjeId() {
        return virtualnoNatjecanjeId;
    }
    public void setVirtualnoNatjecanjeId(Integer virtualnoNatjecanjeId) { this.virtualnoNatjecanjeId = virtualnoNatjecanjeId;}
    public Integer getOrginalnoNatjecanjeId() {
        return orginalnoNatjecanjeId;
    }
    public void setOrginalnoNatjecanjeId(Integer orginalnoNatjecanjeId) { this.orginalnoNatjecanjeId = orginalnoNatjecanjeId;}
    public String getKorisnickoImeNatjecatelja() { return korisnickoImeNatjecatelja;}
    public void setKorisnickoImeNatjecatelja(String korisnickoImeNatjecatelja) { this.korisnickoImeNatjecatelja = korisnickoImeNatjecatelja;}
    public Timestamp getVrijemePocetka() {
        return vrijemePocetka;
    }

    public void setVrijemePocetka(Timestamp vrijemePocetka) {
        this.vrijemePocetka = vrijemePocetka;
    }
}
