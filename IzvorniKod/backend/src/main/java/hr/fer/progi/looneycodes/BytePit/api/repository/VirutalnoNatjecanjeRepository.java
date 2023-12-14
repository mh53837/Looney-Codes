package hr.fer.progi.looneycodes.BytePit.api.repository;

import hr.fer.progi.looneycodes.BytePit.api.model.Zadatak;
import hr.fer.progi.looneycodes.BytePit.api.model.VirtualnoNatjecanje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Sučelje za provođenje upita nad tablicom virtualno_natjecanje u bazi.
 *
 * @see VirtualnoNatjecanje
 */

public interface VirutalnoNatjecanjeRepository extends JpaRepository<VirtualnoNatjecanje, Integer> {

    /**
     * Metoda koja pronalazi virtualno natjecanje po id-u
     *
     * @param virtualnoNatjecanjeId
     * @return virtualno natjecanje
     */
    VirtualnoNatjecanje findByVirtualnoNatjecanjeId(Integer virtualnoNatjecanjeId);

    /**
     * Metoda koja pronalazi virtualna natjecanja po id-u natjecatelja
     *
     * @param natjecateljId
     * @return lista virtualnih natjecanja
     */
    @Query("SELECT v FROM VirtualnoNatjecanje v WHERE v.natjecatelj.korisnikId = :natjecateljId")
    List<VirtualnoNatjecanje> findByNatjtecanteljId(@Param("natjecateljId") Integer natjecateljId);

    /**
     * Metoda koja pronalazi virtualna natjecanja po id-u originalnog natjecanja
     *
     * @param origNatId
     * @return lista virtualnih natjecanja
     */
    @Query("SELECT v FROM VirtualnoNatjecanje v WHERE v.orginalnoNatjecanje.natjecanjeId = :origNatId")
    List<VirtualnoNatjecanje> findByOrigNatId(@Param("origNatId") Integer origNatId);

    @Query("select z from VirtualnoNatjecanje v join v.listaZadataka z where v.virtualnoNatjecanjeId = :virtualnoNatjecanjeId")
    List<Zadatak> findZadaciByVirtualnoNatjecanjeId(@Param("virtualnoNatjecanjeId") Integer virtualnoNatjecanjeId);

}
