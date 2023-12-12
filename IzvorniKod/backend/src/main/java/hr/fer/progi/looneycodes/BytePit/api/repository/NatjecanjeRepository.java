package hr.fer.progi.looneycodes.BytePit.api.repository;

import hr.fer.progi.looneycodes.BytePit.api.model.Natjecanje;
import hr.fer.progi.looneycodes.BytePit.api.model.Zadatak;
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

    /**
     * Metoda koja vraća sva natjecanja koja se tek trebaju održati
     *
     * @return lista natjecanja
     */
    @Query("SELECT n FROM Natjecanje n WHERE n.pocetakNatjecanja > CURRENT_TIMESTAMP")
    List<Natjecanje> findUpcomingNatjecanja();

    /**
     * Metoda koja vraća sva natjecanja koja su u tijeku
     *
     * @return lista natjecanja
     */
    @Query("SELECT n FROM Natjecanje n WHERE n.pocetakNatjecanja < CURRENT_TIMESTAMP AND n.krajNatjecanja > CURRENT_TIMESTAMP")
    List<Natjecanje> findOngoingNatjecanja();

    /**
     * Metoda koja vraća sva natjecanja koja su završena
     *
     * @return lista natjecanja
     */
    @Query("SELECT n FROM Natjecanje n WHERE n.krajNatjecanja < CURRENT_TIMESTAMP")
    List<Natjecanje> findFinishedNatjecanja();

    /**
     * Metoda koja pronalazi sve zadatake povezane s zadanim natjecanjem
     * @param natjecanjeId
     * @return lista zadataka
     */

    @Query("select z from Natjecanje n join n.zadaci z where n.natjecanjeId = :natjecanjeId")
    List<Zadatak> findZadaciByNatjecanjeId(@Param("natjecanjeId") Integer natjecanjeId);


}
