package hr.fer.progi.looneycodes.BytePit.api.controller;

import java.util.Set;
import java.util.Optional;
import java.sql.Timestamp;

import hr.fer.progi.looneycodes.BytePit.api.model.Zadatak;

/**
 * NadmetanjeInfoDTO
 */
public class NadmetanjeInfoDTO {
  private boolean virtualno;
  private Optional<Timestamp> krajNatjecanja;

private Set<ZadatakInfo> zadaci;

  public static class ZadatakInfo {
      private int zadatakId;
      private String zadatakIme;

      public ZadatakInfo(Zadatak zad) {
        this.zadatakId = zad.getZadatakId();
        this.zadatakIme = zad.getNazivZadatka();
      }

      public int getZadatakId() {
        return zadatakId;
      }
      public void setZadatakId(int zadatakId) {
        this.zadatakId = zadatakId;
      }
      public String getZadatakIme() {
        return zadatakIme;
      }
      public void setZadatakIme(String zadatakIme) {
        this.zadatakIme = zadatakIme;
      }
    };

  public boolean isVirtualno() {
    return virtualno;
  }

  public void setVirtualno(boolean virtualno) {
    this.virtualno = virtualno;
  }

  public Set<ZadatakInfo> getZadaci() {
    return zadaci;
  }

  public void setZadaci(Set<ZadatakInfo> zadaci) {
    this.zadaci = zadaci;
  }

  public Optional<Timestamp> getKrajNatjecanja() {
    return krajNatjecanja;
  }

  public void setKrajNatjecanja(Optional<Timestamp> krajNatjecanja) {
    this.krajNatjecanja = krajNatjecanja;
  }
}
