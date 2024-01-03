package hr.fer.progi.looneycodes.BytePit.api.controller;

import org.springframework.data.util.Pair;

import java.time.Duration;
import java.util.Map;

public class RangDTO {
    private String username;
    private double ukupniBodovi;
    private int rang;
    private Map<Integer, Double> zadatakBodovi;
    private Duration vrijemeRjesavanja;


    public RangDTO(String username, Map<Integer, Double> zadatakBodovi, Duration vrijemeRjesavanja) {
        this.username = username;
        this.zadatakBodovi = zadatakBodovi;
        this.ukupniBodovi = izracunajUkupneBodove();
        this.vrijemeRjesavanja = vrijemeRjesavanja;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public double getUkupniBodovi() {
        return ukupniBodovi;
    }

    public void setUkupniBodovi(double ukupniBodovi) {
        this.ukupniBodovi = ukupniBodovi;
    }

    public int getRang() {
        return rang;
    }

    public void setRang(int rang) {
        this.rang = rang;
    }

    public Map<Integer, Double> getZadatakBodovi() {
        return zadatakBodovi;
    }

    public void setZadatakBodovi(Map<Integer, Double> zadatakBodovi) {
        this.zadatakBodovi = zadatakBodovi;
    }

    public Duration getVrijemeRjesavanja() {
        return vrijemeRjesavanja;
    }
    public void setVrijemeRjesavanja(Duration vrijemeRjesavanja) {
        this.vrijemeRjesavanja = vrijemeRjesavanja;
    }
    private double izracunajUkupneBodove() {
        double ukupniBodovi = 0;
        for (Map.Entry<Integer, Double> entry : zadatakBodovi.entrySet()) {
            ukupniBodovi += entry.getValue();
        }
        return ukupniBodovi;

    }
    @Override
    public boolean equals(Object o){
        if (o == this) return true;
        if (!(o instanceof RangDTO)) return false;
        RangDTO rangDTO = (RangDTO) o;
        return rangDTO.getUsername().equals(this.username)
                && rangDTO.getUkupniBodovi() == this.ukupniBodovi
                && rangDTO.getRang() == this.rang
                && rangDTO.getZadatakBodovi().equals(this.zadatakBodovi)
                && rangDTO.getVrijemeRjesavanja().equals(this.vrijemeRjesavanja);
    }
}
