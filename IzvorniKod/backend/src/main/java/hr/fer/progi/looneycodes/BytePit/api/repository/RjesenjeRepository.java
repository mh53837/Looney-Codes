package hr.fer.progi.looneycodes.BytePit.api.repository;

import hr.fer.progi.looneycodes.BytePit.api.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    /**
     * Metoda koja pronalazi sva rješenja po natjecatelju i natjecanju.
     * @param natjecatelj - identifikator natjecatelja
     * @param natjecanje - identifikator natjecanja
     * @return lista rješenja zadanog natjecatelja u određenom natjecanju
     */
    @Query("SELECT r FROM Rjesenje r " +
            "WHERE r.rjesenjeId.natjecatelj = :natjecatelj " +
            "AND r.natjecanje = :natjecanje")
    List<Rjesenje> findByNatjecateljAndNatjecanje(@Param("natjecatelj") Korisnik natjecatelj,
                                                  @Param("natjecanje") Natjecanje natjecanje);


    /**
     * Metoda koja pronalazi sva rješenja po zadatku i natjecanju.
     * @param natjecanje - identifikator natjecanja
     * @param zadatak - identifikator zadatka
     * @return lista rješenja zadanog zadatka u određenom natjecanju
     */
    @Query("SELECT r FROM Rjesenje r " +
            "WHERE r.rjesenjeId.zadatak = :zadatak " +
            "AND r.natjecanje = :natjecanje")
    List<Rjesenje> findByNatjecanjeAndZadatak(@Param("natjecanje") Natjecanje natjecanje,
                                              @Param("zadatak")Zadatak zadatak);
//    /**
//    * Metoda koja pronalazi sva rješenja po zadatku i natjecanju.
//    * @param natjecanje - identifikator natjecanja
//    * @param zadatak - identifikator zadatka
//    * @return lista rješenja zadanog zadatka u određenom natjecanju
//    */
//   @Query("SELECT r FROM Rjesenje r " +
//           "WHERE r.rjesenjeId.zadatak = :zadatak " +
//           "AND r.rjesenjeId.natjecatelj = :natjecatelj" +
//           "AND r.natjecanje = :natjecanje")
//   List<Rjesenje> findByNatjecanjeAndZadatakAndNatjecatelj(@Param("natjecanje") Natjecanje natjecanje,
//                                             @Param("zadatak") Zadatak zadatak, 
//                                             @Param("natjecatelj") Korisnik natjecatelj);

    /**
     * Metoda koja pronalazi sva rješenja po zadatku i natjecanju.
     * @param natjecatelj - identifikator natjecatelja
     * @param zadatak - identifikator zadatka
     * @return lista rješenja zadanog zadatka u određenom natjecanju
     */
    @Query("SELECT r FROM Rjesenje r " +
            "WHERE r.rjesenjeId.zadatak = :zadatak " +
            "AND r.rjesenjeId.natjecatelj = :natjecatelj")
    List<Rjesenje> findByNatjecateljAndZadatak(@Param("natjecatelj") Korisnik natjecatelj,
                                              @Param("zadatak")Zadatak zadatak);

    /**
     * Metoda koja pronalazi sva rješenja za zadano natjecanje.
     * @param natjecanjeId - identifikator natjecanja
     * return lista rješenja za zadano natjecanje
     */
    @Query("SELECT r FROM Rjesenje r WHERE r.natjecanje.natjecanjeId = :natjecanjeId")
    List<Rjesenje> findByNatjecanjeId(@Param("natjecanjeId") Integer natjecanjeId);

}