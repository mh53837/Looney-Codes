package hr.fer.progi.looneycodes.BytePit.api.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.ManyToOne;

@Embeddable
public class TestniPrimjerKey implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer testniPrimjerRb;
	
	@ManyToOne
	private Zadatak zadatak;

	public TestniPrimjerKey() {
		super();
	}
  
	public TestniPrimjerKey(Integer testniPrimjerRb, Zadatak zadatak) {
		this.testniPrimjerRb = testniPrimjerRb;
		this.zadatak = zadatak;
	}
 
	@Override
	public int hashCode() {
		return Objects.hash(testniPrimjerRb, zadatak.getZadatakId());
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TestniPrimjerKey other = (TestniPrimjerKey) obj;
		return testniPrimjerRb == other.testniPrimjerRb && zadatak.getZadatakId() == other.zadatak.getZadatakId();
	}

	public Integer getTestniPrimjerRb() {
		return testniPrimjerRb;
	}

	public void setTestniPrimjerRb(Integer testniPrimjerRb) {
		this.testniPrimjerRb = testniPrimjerRb;
	}

	public Zadatak getZadatak() {
		return zadatak;
	}

	public void setZadatak(Zadatak zadatak) {
		this.zadatak = zadatak;
	}
}