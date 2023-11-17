package hr.fer.progi.looneycodes.BytePit.api.repository;

import hr.fer.progi.looneycodes.BytePit.api.model.Natjecanje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Sučelje za provođenje upita nad tablicom natjecanje u bazi.
 *
 * @see Natjecanje
 */
public interface NatjecanjeRepository extends JpaRepository<Natjecanje, Integer> {
    /**
     * Metoda koja pronalazi natjecanje po id-u
     *
     * @param natjecanjeId
     * @return natjecanje
     */
    Natjecanje findByNatjecanjeId(Integer natjecanjeId);

    /**
     * Metoda koja vraća sva natjecanja povezana s zadanim voditeljem
     *
     * @param korisnikId
     * @return lista natjecanja
     */
    @Query("SELECT n FROM Natjecanje n WHERE n.voditelj.korisnikId = :korisnikId")
    List<Natjecanje> findByKorisnikId(@Param("korisnikId") Integer korisnikId);

    /*@Transactional
    void deleteByNatjecanjeId(Integer natjecanjeId);*/
}