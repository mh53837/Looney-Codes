package hr.fer.progi.looneycodes.BytePit.api.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;


@Embeddable
public class RjesenjeKey implements Serializable {


  private static final long serialVersionUID = 1L;

  private Integer rjesenjeRb;

  @ManyToOne
  private Korisnik natjecatelj;

  @ManyToOne
  private Zadatak zadatak;

  @ManyToOne
  private  Natjecanje natjecanje;

  public RjesenjeKey() {
    super();
  }

  public RjesenjeKey(Integer rjesenjeRb, Korisnik natjecatelj, Zadatak zadatak) {
    this.rjesenjeRb = rjesenjeRb;
    this.natjecatelj = natjecatelj;
    this.zadatak = zadatak;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    RjesenjeKey that = (RjesenjeKey) o;
    return rjesenjeRb == that.rjesenjeRb && Objects.equals(natjecatelj, that.natjecatelj) && Objects.equals(zadatak, that.zadatak);
  }

  @Override
  public int hashCode() {
    return Objects.hash(rjesenjeRb, natjecatelj, zadatak);
  }

  public Integer getRjesenjeRb() { return rjesenjeRb; }

  public void setRjesenjeRb(Integer rjesenjeRb) { this.rjesenjeRb = rjesenjeRb; }

  public Korisnik getNatjecatelj() { return natjecatelj; }

  public void setNatjecatelj(Korisnik natjecatelj) { this.natjecatelj = natjecatelj; }

  public Zadatak getZadatak() { return zadatak; }

  public void setZadatak(Zadatak zadatak) { this.zadatak = zadatak; }
}
