package hr.fer.progi.looneycodes.BytePit.api.controller;

import hr.fer.progi.looneycodes.BytePit.api.model.Korisnik;
import hr.fer.progi.looneycodes.BytePit.api.model.Natjecanje;

/**
 * Objekt koji se preda routeru prilikom dodavanja pehara
 */
public class AddPeharDTO {
    /**
     * korisničko ime natjecatelja koji je dobio pehar kojeg dodajemo
     */
    private String korisnickoImeNatjecatelja;
    /**
     * Id natjecanja za koje je vezan pehar kojeg dodajemo
     */
    private Integer natjecanjeId;
    /**
     * mjesto za koje je pehar kojeg dodajemo dodijeljen
     */
    private int mjesto;
    /**
     * slika pehara kojeg dodajemo
     */
    private String slikaPehara;

    public String getKorisnickoImeNatjecatelja() {
        return korisnickoImeNatjecatelja;
    }

    public Integer getNatjecanjeId() {
        return natjecanjeId;
    }

    public int getMjesto() {
        return mjesto;
    }

    public String getSlikaPehara() {
        return slikaPehara;
    }

    public void setKorisnickoImeNatjecatelja(String korisnickoImeNatjecatelja) {
        this.korisnickoImeNatjecatelja = korisnickoImeNatjecatelja;
    }

    public void setNatjecanjeId(Integer natjecanjeId) {
        this.natjecanjeId = natjecanjeId;
    }

    public void setMjesto(int mjesto) {
        this.mjesto = mjesto;
    }

    public void setSlikaPehara(String slikaPehara) {
        this.slikaPehara = slikaPehara;
    }
}
