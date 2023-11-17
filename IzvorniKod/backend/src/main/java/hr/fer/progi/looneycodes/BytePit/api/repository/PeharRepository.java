package hr.fer.progi.looneycodes.BytePit.api.repository;

import hr.fer.progi.looneycodes.BytePit.api.model.Korisnik;
import hr.fer.progi.looneycodes.BytePit.api.model.Pehar;
import hr.fer.progi.looneycodes.BytePit.api.model.Zadatak;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
/**
 * Sučelje za provođenje upita nad tablicom pehar u bazi.
 */

public interface PeharRepository extends JpaRepository<Pehar, Integer> {

    /**
     * Metoda koja pronalazi sve pehare.
     * @return lista svih pehara
     */
    @Query("SELECT p FROM Pehar p")
    List<Pehar> findAllTrophies();

    /**
     * Metoda koja pronalazi pehare jednog korisnika.
     * @param natjecatelj - identifikator korisnik za kojeg se traže pehari
     * @return listu pehara jednog korisnika
     */
    List<Pehar> findTrophiesByNatjecatelj(Korisnik natjecatelj);
}
