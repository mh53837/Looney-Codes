package hr.fer.progi.looneycodes.BytePit.api.controller;


import hr.fer.progi.looneycodes.BytePit.api.model.VirtualnoNatjecanje;
import hr.fer.progi.looneycodes.BytePit.service.VirtualnoNatjecanjeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * Kontroler koji se koristi za pristup metodama vezanim uz VirtualnoNatjecanje entitet.
 *
 * @see VirtualnoNatjecanjeService
 * @see VirtualnoNatjecanje
 */
@RestController
@RequestMapping("/virtualnaNatjecanja")
public class VirtualnoNatjecanjeController {

    @Autowired
    private VirtualnoNatjecanjeService virtualnoNatjecanjeService;

    /**
     * Stvara virtualno natjecanje i sprema ga u bazu
     *
     * @param virtualnoNatjecanjeDTO objekt s podacima o virtualnom natjecanju
     * @return virtualno natjecanje
     */
    @PostMapping("/new")
    public VirtualnoNatjecanje createVirtualnoNatjecanje(@RequestBody VirtualnoNatjecanjeDTO virtualnoNatjecanjeDTO) {
        return virtualnoNatjecanjeService.createVirtualnoNatjecanje(virtualnoNatjecanjeDTO);
    }

    /**
     * Vraća sva virtualna natjecanja
     *
     * @return lista virtualnih natjecanja
     */
    @GetMapping("/all")
    public List<VirtualnoNatjecanjeDTO> listAll() {
        return virtualnoNatjecanjeService.findAll().stream().map(virtualnoNatjecanje -> {
            return new VirtualnoNatjecanjeDTO(virtualnoNatjecanje.getVirtualnoNatjecanjeId(), virtualnoNatjecanje.getOrginalnoNatjecanje().getNatjecanjeId(), virtualnoNatjecanje.getNatjecatelj().getKorisnikId(), virtualnoNatjecanje.getVrijemePocetka());
        }).toList();
    }

    /**
     * Vraća virtualno natjecanje prema jedinstvenom identifikatoru
     *
     * @param Id identifikator virtualnog natjecanja
     * @return virtualno natjecanje
     */
    @GetMapping("/get/{Id}")
    public VirtualnoNatjecanjeDTO getVirtualnoNatjecanje(@PathVariable Integer Id) {
        VirtualnoNatjecanje virtualnoNatjecanje = virtualnoNatjecanjeService.getVirtualnoNatjecanje(Id);
        return new VirtualnoNatjecanjeDTO(virtualnoNatjecanje.getVirtualnoNatjecanjeId(), virtualnoNatjecanje.getOrginalnoNatjecanje().getNatjecanjeId(), virtualnoNatjecanje.getNatjecatelj().getKorisnikId(), virtualnoNatjecanje.getVrijemePocetka());
    }

    /**
     * Vraća virtualna natjecanja povezana s zadanim natjecateljem
     *
     * @param natjecateljId identifikator natjecatelja
     * @return lista virtualnih natjecanja
     */
    @GetMapping("/get/natjecatelj/{natjecateljId}")
    public List<VirtualnoNatjecanjeDTO> getVirtualnoNatjecanjeByKorisnikId(@PathVariable Integer natjecateljId) {
        return virtualnoNatjecanjeService.getByKorisnikId(natjecateljId).stream().map(virtualnoNatjecanje -> {
            return new VirtualnoNatjecanjeDTO(virtualnoNatjecanje.getVirtualnoNatjecanjeId(), virtualnoNatjecanje.getOrginalnoNatjecanje().getNatjecanjeId(), virtualnoNatjecanje.getNatjecatelj().getKorisnikId(), virtualnoNatjecanje.getVrijemePocetka());
        }).toList();
    }

    /**
     * Vraća virtualna natjecanja povezana s zadanim originalnim natjecanjem
     *
     * @param origNatId identifikator originalnog natjecanja
     * @return lista virtualnih natjecanja
     */
    @GetMapping("/get/origNatjecanje/{origNatId}")
    public List<VirtualnoNatjecanjeDTO> getVirtualnoNatjecanjeByOrigNatId(@PathVariable Integer origNatId) {
        return virtualnoNatjecanjeService.getByOrigNatId(origNatId).stream().map(virtualnoNatjecanje -> {
            return new VirtualnoNatjecanjeDTO(virtualnoNatjecanje.getVirtualnoNatjecanjeId(), virtualnoNatjecanje.getOrginalnoNatjecanje().getNatjecanjeId(), virtualnoNatjecanje.getNatjecatelj().getKorisnikId(), virtualnoNatjecanje.getVrijemePocetka());
        }).toList();
    }

}
