package hr.fer.progi.looneycodes.BytePit.api.model;

// spring-boot imports
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

// java imports
import java.sql.Timestamp;
import java.util.Set;


/**
 * Entitet koji definira natjecanje, identifikator, vrijeme pocetka i kraja
 * TODO: povezivanje s bazom, testiranje
 */
@Entity
public class Natjecanje extends Nadmetanje{

	/**
	 * naziv natjecanja
	 */
	@Column(nullable = false)
	private String nazivNatjecanja;
	/**
	 * vrijeme kraja natjecanja
	 */
	private Timestamp krajNatjecanja;
	
	
    public Natjecanje() {
    }
    public Natjecanje(Korisnik voditelj, String nazivNatjecanja, Timestamp pocetakNatjecanja, Timestamp krajNatjecanja) {
        super(voditelj, pocetakNatjecanja);
    	this.nazivNatjecanja = nazivNatjecanja;
        this.krajNatjecanja = krajNatjecanja;
    }
    
    public Korisnik getVoditelj() {
    	return getKorisnik();
    }
    
    public void setVoditelj(Korisnik voditelj) {
    	setKorisnik(voditelj);
    }
    public Timestamp getKrajNatjecanja() {
        return krajNatjecanja;
      }
    public void setKrajNatjecanja(Timestamp krajNatjecanja) {
      if(krajNatjecanja.compareTo(this.getPocetakNatjecanja()) <= 0)
        throw new IllegalArgumentException();

        this.krajNatjecanja = krajNatjecanja;
    }
    @Override
    public void setPocetakNatjecanja(Timestamp pocetakNatjecanja) {
        if(pocetakNatjecanja.compareTo(this.krajNatjecanja) >= 0)
          throw new IllegalArgumentException();

        super.setPocetakNatjecanja(pocetakNatjecanja);
    }
    public String getNazivNatjecanja() {
      return nazivNatjecanja;
    }
    public void setNazivNatjecanja(String nazivNatjecanja) {
      this.nazivNatjecanja = nazivNatjecanja;
    }
}
