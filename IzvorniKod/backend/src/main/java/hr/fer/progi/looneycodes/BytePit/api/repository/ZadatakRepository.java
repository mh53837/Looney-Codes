package hr.fer.progi.looneycodes.BytePit.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import hr.fer.progi.looneycodes.BytePit.api.model.Korisnik;
import hr.fer.progi.looneycodes.BytePit.api.model.Natjecanje;
import hr.fer.progi.looneycodes.BytePit.api.model.Zadatak;

/**
 * Sučelje za provođenje upita nad tablicom zadatak u bazi.
 */
public interface ZadatakRepository extends JpaRepository<Zadatak, Integer> {
	/**
	   * Metoda koja pronalazi sve javna zadatke.
	   * @return lista zadataka koji nisu privatni
	   */
	@Query("SELECT z FROM Zadatak z WHERE z.privatniZadatak = FALSE")
	List<Zadatak> findAllJavniZadatak();
	/**
	   * Metoda koja pronalazi zadatke iz jednog natjecanja.
	   * @param natjecanje - natjecanja za koje se traže zadaci
	   * @return listu zadataka iz jednog natjecanja 
	   */
	List<Zadatak> findByNatjecanje(Natjecanje natjecanje);
	/**
	   * Metoda koja pronalazi zadatke jednog voditelja.
	   * @param natjecanje - identifikator voditelja za kojeg se traže zadaci
	   * @return listu zadataka jednog voditelja
	   */
	List<Zadatak> findByVoditelj(Korisnik vodiitelj);

}
