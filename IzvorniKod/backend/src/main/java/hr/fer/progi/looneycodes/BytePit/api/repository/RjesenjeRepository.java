package hr.fer.progi.looneycodes.BytePit.api.repository;

import hr.fer.progi.looneycodes.BytePit.api.model.Korisnik;
import hr.fer.progi.looneycodes.BytePit.api.model.Rjesenje;
import hr.fer.progi.looneycodes.BytePit.api.model.RjesenjeKey;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Sučelje za provođenje upita nad tablicom Rjesenje u bazi.
 */

public interface RjesenjeRepository extends JpaRepository<Rjesenje, RjesenjeKey> {
    /**
     * Metoda koja pronalazi sva rješenja po natjecatelju.
     * @param natjecatelj - identifikator natjecatelja
     * @return lista rješenja zadanog natjecatelja
     */
    List<Rjesenje> findByRjesenjeIdNatjecatelj(Korisnik natjecatelj);

}