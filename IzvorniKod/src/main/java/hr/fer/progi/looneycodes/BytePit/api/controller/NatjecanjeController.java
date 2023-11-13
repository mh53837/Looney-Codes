package hr.fer.progi.looneycodes.BytePit.api.controller;

import hr.fer.progi.looneycodes.BytePit.api.model.Natjecanje;
import hr.fer.progi.looneycodes.BytePit.service.NatjecanjeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Kontroler koji se koristi za pristup metodama vezanim uz Natjecanje entitet.
 *
 * @see NatjecanjeService
 * @see Natjecanje
 */
@RestController
@RequestMapping("/natjecanja")
public class NatjecanjeController {

    @Autowired
    private NatjecanjeService natjecanjeService;

    /**
     * Stvara natjecanje i sprema ga u bazu
     *
     * @param natjecanjeDTO objekt s podacima o natjecanju
     * @return natjecanje
     */
    @PostMapping("/new")
    public Natjecanje createNatjecanje(@RequestBody CreateNatjecanjeDTO natjecanjeDTO) {
        return natjecanjeService.createNatjecanje(natjecanjeDTO);
    }

    /**
     * Vraća natjecanje prema jedinstvenom identifikatoru
     *
     * @param natjecanjeId identifikator natjecanja
     * @return natjecanje
     */
    @GetMapping("/get/{natjecanjeId}")
    public CreateNatjecanjeDTO getNatjecanje(@PathVariable Integer natjecanjeId) {
        Natjecanje natjecanje = natjecanjeService.getNatjecanje(natjecanjeId);
        return new CreateNatjecanjeDTO(natjecanje.getNatjecanjeId(), natjecanje.getNazivNatjecanja(), natjecanje.getPocetakNatjecanja(), natjecanje.getKrajNatjecanja(), natjecanje.getVoditelj().getKorisnikId());
    }

    /**
     * Vraća sva natjecanja
     *
     * @return lista natjecanja
     */
    @GetMapping("/all")
    public List<CreateNatjecanjeDTO> listAll() {
        return natjecanjeService.listAllNatjecanja().stream().map(natjecanje -> {
            return new CreateNatjecanjeDTO(natjecanje.getNatjecanjeId(), natjecanje.getNazivNatjecanja(), natjecanje.getPocetakNatjecanja(), natjecanje.getKrajNatjecanja(), natjecanje.getVoditelj().getKorisnikId());
        }).toList();
    }

    /**
     * Vraća natjecanja povezana s zadanim voditeljem
     *
     * @param voditeljId
     * @return lista natjecanja
     */
    @GetMapping("/get/voditelj/{voditeljId}")
    public List<CreateNatjecanjeDTO> getNatjecanjaByKorisnikId(@PathVariable Integer voditeljId) {
        return natjecanjeService.getNatjecanjaByKorisnikId(voditeljId).stream().map(natjecanje -> {
            return new CreateNatjecanjeDTO(natjecanje.getNatjecanjeId(), natjecanje.getNazivNatjecanja(), natjecanje.getPocetakNatjecanja(), natjecanje.getKrajNatjecanja(), natjecanje.getVoditelj().getKorisnikId());
        }).toList();
    }

    /**
     * Metoda koja ažurira postojeće natjecanje
     *
     * @param natjecanjeDTO DTO objekt s ažuriranim podacima o natjecanju
     * @return referenca na azurirano natjecanje
     */
    @PostMapping("/update")
    public Natjecanje updateNatjecanje(@RequestBody CreateNatjecanjeDTO natjecanjeDTO) {
        return natjecanjeService.updateNatjecanje(natjecanjeDTO);
    }

    /*@DeleteMapping("/delete/{natjecanjeId}")
    public void deleteNatjecanje(@PathVariable Integer natjecanjeId) {
        natjecanjeService.deleteNatjecanje(natjecanjeId);
    }*/

}

