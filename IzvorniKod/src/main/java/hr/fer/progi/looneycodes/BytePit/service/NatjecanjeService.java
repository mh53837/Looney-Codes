package hr.fer.progi.looneycodes.BytePit.service;

import hr.fer.progi.looneycodes.BytePit.api.controller.CreateNatjecanjeDTO;
import hr.fer.progi.looneycodes.BytePit.api.model.Natjecanje;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public interface NatjecanjeService {
    /**
     * Stvori novo natjecanje i spremi ga u bazu.
     * @param natjecanjeDTO DTO objekt sa svim podacima o natjecanju
     * @return referenca na stvoreno natjecanje
     */
     public Natjecanje createNatjecanje(CreateNatjecanjeDTO natjecanjeDTO);
    /**
     * Metoda koja ažurira postojeće natjecanje
     * @param natjecanjeDTO DTO objekt s ažuriranim podacima o natjecanju
     * @return referenca na azurirano natjecanje
     */
    public Natjecanje updateNatjecanje(CreateNatjecanjeDTO natjecanjeDTO);
    /**
     * Dohvaća natjecanje prema jedinstvenom identifikatoru
     * @param natjecanjeId jedinstveni identifikator natjecanja
     * @return natjecanje
     */
    public Natjecanje getNatjecanje(Integer natjecanjeId);
    /**
     * Dohvaća sva natjecanja
     * @return lista natjecanja
     */
    public List<Natjecanje> getAllNatjecanja();
    /**
     * Dohvaća sva natjecanja povezana s zadanim voditeljem
     * @param korisnikId
     * @return lista natjecanja
     */
    public List<Natjecanje> getNatjecanjaByKorisnikId(Integer korisnikId);
    /**
     * Briše natjecanje prema jedinstvenom identifikatoru
     * @param natjecanjeId - jedinstveni identifikator natjecanja
     */
    public void deleteNatjecanje(Integer natjecanjeId);


}
