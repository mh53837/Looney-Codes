package hr.fer.progi.looneycodes.BytePit.api.controller;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hr.fer.progi.looneycodes.BytePit.api.model.Korisnik;
import hr.fer.progi.looneycodes.BytePit.api.model.Pehar;

/**
 * Objekt koji se preda routeru prilikom dodavanja pehara
 */
public class AddPeharDTO {

    public AddPeharDTO() {
    }
    public AddPeharDTO(Pehar pehar)
    {
        Korisnik natjecatelj = pehar.getNatjecatelj();
        this.korisnickoImeNatjecatelja = natjecatelj ==null ? null : natjecatelj.getKorisnickoIme();
        this.natjecanjeId = pehar.getNatjecanje().getNatjecanjeId();
        this.mjesto = pehar.getMjesto();
        this.slikaPehara = pehar.getSlikaPehara();
        this.peharId = pehar.getPeharId();
    }
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
    @JsonIgnore
    private String slikaPehara;

    private Integer peharId;

    public Integer getPeharId() { return peharId;}

    public void setPeharId(Integer peharId) { this.peharId = peharId;}

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
