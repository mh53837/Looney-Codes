package hr.fer.progi.looneycodes.BytePit.service;

import hr.fer.progi.looneycodes.BytePit.api.model.Zadatak;
import hr.fer.progi.looneycodes.BytePit.api.controller.VirtualnoNatjecanjeDTO;
import hr.fer.progi.looneycodes.BytePit.api.model.VirtualnoNatjecanje;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * Interface koje definira komunikaciju s bazom podataka u odnosu na Virtualno_natjecanje entitete
 */
@Service
public interface VirtualnoNatjecanjeService {
    /**
     * Vraća sva virtualna natjecanja
     *
     * @return lista virtualnih natjecanja
     */
    List<VirtualnoNatjecanje> findAll();

    /**
     * Vraća virtualno natjecanje prema jedinstvenom identifikatoru
     *
     * @param Id identifikator virtualnog natjecanja
     * @return virtualno natjecanje
     */

    VirtualnoNatjecanje getVirtualnoNatjecanje(Integer Id);

    /**
     * Vraća virtualna natjecanja povezana s zadanim natjecateljem
     *
     * @param korisnikId identifikator natjecatelja
     * @return lista virtualnih natjecanja
     */

    List<VirtualnoNatjecanje> getByKorisnikId(Integer korisnikId);

    /**
     * Stvara virtualno natjecanje i sprema ga u bazu
     *
     * @param virtualnoNatjecanjeDTO DTO objekt s podacima o virtualnom natjecanju
     * @return lista virtualnih natjecanja
     */

    VirtualnoNatjecanje createVirtualnoNatjecanje(VirtualnoNatjecanjeDTO virtualnoNatjecanjeDTO);

    /**
     * Vraća virtualna natjecanja povezana s zadanim originalnim natjecanjem
     *
     * @param origNatId identifikator originalnog natjecanja
     * @return lista virtualnih natjecanja
     */
    List<VirtualnoNatjecanje> getByOrigNatId(Integer origNatId);

    /**
     * Vraća sve zadatke povezane s virtualnim natjecanjem
     * @param virtualnoNatjecanjeId identifikator virtualnog natjecanja
     * @return lista zadataka
     *
     */
    Set<Zadatak> getZadaci(Integer virtualnoNatjecanjeId);

    /**
     * Stvara virtualno natjecanje s nasumičnim zadacima i sprema ga u bazu
     * @param korisnickoImeNatjecatelja korisničko ime natjecatelja
     *
     */

    VirtualnoNatjecanje createVirtualnoNatjecanjeRandom(String korisnickoImeNatjecatelja);
}
