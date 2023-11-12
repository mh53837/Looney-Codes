package hr.fer.progi.looneycodes.BytePit.api.repository;


import hr.fer.progi.looneycodes.BytePit.api.model.VirtualnoNatjecanje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface VirutalnoNatjecanjeRepository extends JpaRepository<VirtualnoNatjecanje, Integer> {

    VirtualnoNatjecanje findByVirtualnoNatjecanjeId(Integer virtualnoNatjecanjeId);

    @Query("SELECT v FROM VirtualnoNatjecanje v WHERE v.natjecatelj.korisnikId = :natjecateljId")
    List<VirtualnoNatjecanje> findByNatjtecanteljId(@Param("natjecateljId") Integer natjecateljId);

    @Query("SELECT v FROM VirtualnoNatjecanje v WHERE v.orginalnoNatjecanje.natjecanjeId = :origNatId")
    List<VirtualnoNatjecanje> findByOrigNatId(@Param("origNatId") Integer origNatId);


}
