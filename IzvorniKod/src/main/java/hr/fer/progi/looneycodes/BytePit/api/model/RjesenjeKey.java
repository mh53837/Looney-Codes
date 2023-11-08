package hr.fer.progi.looneycodes.BytePit.api.model;

import jakarta.persistence.*;
import java.io.Serializable;

@Embeddable
public class RjesenjeKey implements Serializable {

  private int rjesenjeRb;

  private int natjecateljId;

  private int zadatakId;
}