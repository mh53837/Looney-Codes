package hr.fer.progi.looneycodes.BytePit.api.controller;


import hr.fer.progi.looneycodes.BytePit.api.model.VirtualnoNatjecanje;
import hr.fer.progi.looneycodes.BytePit.service.VirtualnoNatjecanjeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/virtualnaNatjecanja")
public class VirtualnoNatjecanjeController {

    @Autowired
    private VirtualnoNatjecanjeService virtualnoNatjecanjeService;

    @PostMapping("/new")
    public VirtualnoNatjecanje createVirtualnoNatjecanje(@RequestBody VirtualnoNatjecanjeDTO virtualnoNatjecanjeDTO) {
        return virtualnoNatjecanjeService.createVirtualnoNatjecanje(virtualnoNatjecanjeDTO);
    }

    @GetMapping("/all")
    public List<VirtualnoNatjecanjeDTO> listAll() {
        return virtualnoNatjecanjeService.findAll().stream().map(virtualnoNatjecanje -> {
            return new VirtualnoNatjecanjeDTO(virtualnoNatjecanje.getVirtualnoNatjecanjeId(), virtualnoNatjecanje.getOrginalnoNatjecanje().getNatjecanjeId(), virtualnoNatjecanje.getNatjecatelj().getKorisnikId(), virtualnoNatjecanje.getVrijemePocetka());
        }).toList();
    }

    @GetMapping("/get/{Id}")
    public VirtualnoNatjecanjeDTO getVirtualnoNatjecanje(@PathVariable Integer Id) {
        VirtualnoNatjecanje virtualnoNatjecanje = virtualnoNatjecanjeService.getVirtualnoNatjecanje(Id);
        return new VirtualnoNatjecanjeDTO(virtualnoNatjecanje.getVirtualnoNatjecanjeId(), virtualnoNatjecanje.getOrginalnoNatjecanje().getNatjecanjeId(), virtualnoNatjecanje.getNatjecatelj().getKorisnikId(), virtualnoNatjecanje.getVrijemePocetka());
    }

    @GetMapping("/get/natjecatelj/{natjecateljId}")
    public List<VirtualnoNatjecanjeDTO> getVirtualnoNatjecanjeByKorisnikId(@PathVariable Integer natjecateljId) {
        return virtualnoNatjecanjeService.getByKorisnikId(natjecateljId).stream().map(virtualnoNatjecanje -> {
            return new VirtualnoNatjecanjeDTO(virtualnoNatjecanje.getVirtualnoNatjecanjeId(), virtualnoNatjecanje.getOrginalnoNatjecanje().getNatjecanjeId(), virtualnoNatjecanje.getNatjecatelj().getKorisnikId(), virtualnoNatjecanje.getVrijemePocetka());
        }).toList();
    }

    @GetMapping("/get/origNatjecanje/{origNatId}")
    public List<VirtualnoNatjecanjeDTO> getVirtualnoNatjecanjeByOrigNatId(@PathVariable Integer origNatId) {
        return virtualnoNatjecanjeService.getByOrigNatId(origNatId).stream().map(virtualnoNatjecanje -> {
            return new VirtualnoNatjecanjeDTO(virtualnoNatjecanje.getVirtualnoNatjecanjeId(), virtualnoNatjecanje.getOrginalnoNatjecanje().getNatjecanjeId(), virtualnoNatjecanje.getNatjecatelj().getKorisnikId(), virtualnoNatjecanje.getVrijemePocetka());
        }).toList();
    }

}
