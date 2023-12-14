package hr.fer.progi.looneycodes.BytePit.api.controller;

import hr.fer.progi.looneycodes.BytePit.api.model.VirtualnoNatjecanje;

import java.sql.Timestamp;

public class VirtualnoNatjecanjeDTO {
    private Integer virtualnoNatjecanjeId;
    private Integer orginalnoNatjecanjeId;
    private Integer natjecateljId;
    Timestamp vrijemePocetka;

    public VirtualnoNatjecanjeDTO() {
    }

    public VirtualnoNatjecanjeDTO(Integer virtualnoNatjecanjeId, Integer orginalnoNatjecanjeId, Integer natjecateljId, Timestamp vrijemePocetka) {
        this.virtualnoNatjecanjeId = virtualnoNatjecanjeId;
        this.orginalnoNatjecanjeId = orginalnoNatjecanjeId;
        this.natjecateljId = natjecateljId;
        this.vrijemePocetka = vrijemePocetka;
    }

    public VirtualnoNatjecanjeDTO(VirtualnoNatjecanje virtualnoNatjecanje){
        this.virtualnoNatjecanjeId = virtualnoNatjecanje.getVirtualnoNatjecanjeId();
        this.orginalnoNatjecanjeId = virtualnoNatjecanje.getOrginalnoNatjecanje().getNatjecanjeId();
        this.natjecateljId = virtualnoNatjecanje.getNatjecatelj().getKorisnikId();
        this.vrijemePocetka = virtualnoNatjecanje.getVrijemePocetka();
    }

    public Integer getVirtualnoNatjecanjeId() {
        return virtualnoNatjecanjeId;
    }

    public void setVirtualnoNatjecanjeId(Integer virtualnoNatjecanjeId) {
        this.virtualnoNatjecanjeId = virtualnoNatjecanjeId;
    }

    public Integer getOrginalnoNatjecanjeId() {
        return orginalnoNatjecanjeId;
    }

    public void setOrginalnoNatjecanjeId(Integer orginalnoNatjecanjeId) {
        this.orginalnoNatjecanjeId = orginalnoNatjecanjeId;
    }

    public Integer getNatjecateljId() {
        return natjecateljId;
    }

    public void setNatjecateljId(Integer natjecateljId) {
        this.natjecateljId = natjecateljId;
    }

    public Timestamp getVrijemePocetka() {
        return vrijemePocetka;
    }

    public void setVrijemePocetka(Timestamp vrijemePocetka) {
        this.vrijemePocetka = vrijemePocetka;
    }
}
