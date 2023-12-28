package hr.fer.progi.looneycodes.BytePit.service;

import hr.fer.progi.looneycodes.BytePit.api.controller.CreateNatjecanjeDTO;
import hr.fer.progi.looneycodes.BytePit.api.controller.RangDTO;
import hr.fer.progi.looneycodes.BytePit.api.model.Natjecanje;
import hr.fer.progi.looneycodes.BytePit.api.model.Zadatak;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * Interface koje definira komunikaciju s bazom podataka u odnosu na Natjecanje entitete
 */
@Service
public interface NatjecanjeService {
    /**
     * Stvori novo natjecanje i spremi ga u bazu.
     *
     * @param natjecanjeDTO DTO objekt sa svim podacima o natjecanju
     * @return referenca na stvoreno natjecanje
     */
    Natjecanje createNatjecanje(CreateNatjecanjeDTO natjecanjeDTO);

    /**
     * Metoda koja ažurira postojeće natjecanje
     *
     * @param natjecanjeDTO DTO objekt s ažuriranim podacima o natjecanju
     * @return referenca na azurirano natjecanje
     */
    Natjecanje updateNatjecanje(CreateNatjecanjeDTO natjecanjeDTO);

    /**
     * Dohvaća natjecanje prema jedinstvenom identifikatoru
     *
     * @param natjecanjeId jedinstveni identifikator natjecanja
     * @return natjecanje
     */
    Natjecanje getNatjecanje(Integer natjecanjeId);

    /**
     * Dohvaća sva natjecanja
     *
     * @return lista natjecanja
     */
    List<Natjecanje> listAllNatjecanja();

    /**
     * Dohvaća sva natjecanja povezana s zadanim voditeljem
     *
     * @param korisnikId
     * @return lista natjecanja
     */
    List<Natjecanje> getNatjecanjaByKorisnikId(Integer korisnikId);

    /**
     * Dohvaća sva natjecanja koja se tek trebaju održati
     *
     * @return lista natjecanja
     */

    List<Natjecanje> getUpcomingNatjecanja();

    /**
     * Dohvaća sva natjecanja koja su u tijeku
     *
     * @return lista natjecanja
     */
    List<Natjecanje> getOngoingNatjecanja();

    /**
     * Dohvaća sva natjecanja koja su završena
     *
     * @return lista natjecanja
     */
    List<Natjecanje> getFinishedNatjecanja();

    /**
     * Metoda koja pronalazi sve zadatake povezane s zadanim natjecanjem
     * @param natjecanjeId
     * @return lista zadataka
     */
    public Set<Zadatak> getZadaciByNatjecanjeId(Integer natjecanjeId);

    /**
     * Metoda koja dodaje zadatak u natjecanje
     * @param natjecanjeId
     * @param zadatakId
     */
    public void addZadatakToNatjecanje(Integer natjecanjeId, Integer zadatakId);

    /**
     * Metoda koja uklanja zadatak iz natjecanja
     * @param natjecanjeId
     * @param zadatakId
     */

    public void removeZadatakFromNatjecanje(Integer natjecanjeId, Integer zadatakId);

    /**
     * Metoda koja dohvaća rang listu za zadano natjecanje
     * @param natjecanjeId
     * @return lista rangova
     */

    public List<RangDTO> getRangList(Integer natjecanjeId);



}
