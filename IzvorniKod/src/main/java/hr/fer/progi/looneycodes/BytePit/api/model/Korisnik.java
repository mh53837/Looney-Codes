package hr.fer.progi.looneycodes.BytePit.api.model;

import jakarta.persistence.*;

/**
 * Entitet koji definira naseg korisnika, onako kako je u bazi definiran.
 * TODO: dodaj sve atribute i getere (ER shema?)
 * TODO: povezivanje s bazom, testiranje
 */
@Entity
public class Korisnik{
  @Id
  @GeneratedValue
  public int id;
}
