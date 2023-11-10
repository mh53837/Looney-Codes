package hr.fer.progi.looneycodes.BytePit.api.repository;

import hr.fer.progi.looneycodes.BytePit.api.controller.CreateNatjecanjeDTO;
import hr.fer.progi.looneycodes.BytePit.api.model.Natjecanje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NatjecanjeRepository extends JpaRepository<Natjecanje, Integer> {
    Natjecanje findByNatjecanjeId(Integer natjecanjeId);

    @Query("SELECT n FROM Natjecanje n WHERE n.voditelj.korisnikId = :korisnikId")
    List<Natjecanje> findByKorisnikId(@Param("korisnikId") Integer korisnikId);


    @Modifying
    @Query("DELETE FROM Natjecanje n WHERE n.natjecanjeId = :natjecanjeid")
    void deleteByNatjecanjeId(@Param("natjecanjeid") Integer natjecanjeid);




}
