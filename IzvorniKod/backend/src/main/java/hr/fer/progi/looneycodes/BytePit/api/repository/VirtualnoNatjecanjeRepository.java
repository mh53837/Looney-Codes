package hr.fer.progi.looneycodes.BytePit.api.repository;

import hr.fer.progi.looneycodes.BytePit.api.model.Zadatak;
import hr.fer.progi.looneycodes.BytePit.api.model.VirtualnoNatjecanje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

/**
 * Sučelje za provođenje upita nad tablicom virtualno_natjecanje u bazi.
 *
 * @see VirtualnoNatjecanje
 */

public interface VirtualnoNatjecanjeRepository extends JpaRepository<VirtualnoNatjecanje, Integer> {

    /**
     * Metoda koja pronalazi virtualno natjecanje po id-u
     *
     * @param virtualnoNatjecanjeId
     * @return virtualno natjecanje
     */
    VirtualnoNatjecanje findByNatjecanjeId(Integer virtualnoNatjecanjeId);

    /**
     * Metoda koja pronalazi virtualna natjecanja po id-u natjecatelja
     *
     * @param natjecateljId
     * @return lista virtualnih natjecanja
     */
    @Query("SELECT v FROM VirtualnoNatjecanje v WHERE v.korisnik.korisnikId = :natjecateljId")
    List<VirtualnoNatjecanje> findByNatjtecanteljId(@Param("natjecateljId") Integer natjecateljId);

    /**
     * Metoda koja pronalazi virtualna natjecanja po id-u originalnog natjecanja
     *
     * @param origNatId
     * @return lista virtualnih natjecanja
     */
    @Query("SELECT v FROM VirtualnoNatjecanje v WHERE v.orginalnoNatjecanje.natjecanjeId = :origNatId")
    List<VirtualnoNatjecanje> findByOrigNatId(@Param("origNatId") Integer origNatId);

    @Query("select z from VirtualnoNatjecanje v join v.zadaci z where v.natjecanjeId = :virtualnoNatjecanjeId")
    Set<Zadatak> findZadaciByVirtualnoNatjecanjeId(@Param("virtualnoNatjecanjeId") Integer virtualnoNatjecanjeId);

}
