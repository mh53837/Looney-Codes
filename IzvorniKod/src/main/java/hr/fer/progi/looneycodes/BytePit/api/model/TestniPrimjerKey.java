package hr.fer.progi.looneycodes.BytePit.api.model;

import jakarta.persistence.*;
import java.io.Serializable;

@Embeddable
public class TestniPrimjerKey implements Serializable {

  private int testniPrimjerRb;

  private int zadatakId;
}