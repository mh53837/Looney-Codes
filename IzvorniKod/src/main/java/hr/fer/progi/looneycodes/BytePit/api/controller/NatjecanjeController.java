package hr.fer.progi.looneycodes.BytePit.api.controller;

import hr.fer.progi.looneycodes.BytePit.api.model.Natjecanje;
import hr.fer.progi.looneycodes.BytePit.service.NatjecanjeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/natjecanja")
public class NatjecanjeController {

    @Autowired
    private NatjecanjeService natjecanjeService;

    @PostMapping("/new")
    public Natjecanje createNatjecanje(@RequestBody CreateNatjecanjeDTO natjecanjeDTO){
        return natjecanjeService.createNatjecanje(natjecanjeDTO);
    }
    
    @GetMapping("/get/{natjecanjeId}")
    public CreateNatjecanjeDTO getNatjecanje(@PathVariable Integer natjecanjeId){
        return new CreateNatjecanjeDTO(natjecanjeService.getNatjecanje(natjecanjeId).getNatjecanjeId(), natjecanjeService.getNatjecanje(natjecanjeId).getNazivNatjecanja(), natjecanjeService.getNatjecanje(natjecanjeId).getPocetakNatjecanja(), natjecanjeService.getNatjecanje(natjecanjeId).getKrajNatjecanja(), natjecanjeService.getNatjecanje(natjecanjeId).getVoditelj().getKorisnikId());
    }
    @GetMapping("/all")
    public List<CreateNatjecanjeDTO> getAllNatjecanja(){
        return natjecanjeService.getAllNatjecanja().stream().map(natjecanje -> {
            return new CreateNatjecanjeDTO(natjecanje.getNatjecanjeId(), natjecanje.getNazivNatjecanja(), natjecanje.getPocetakNatjecanja(), natjecanje.getKrajNatjecanja(), natjecanje.getVoditelj().getKorisnikId());
        }).toList();
    }

    @GetMapping("/getByVoditeljId/{voditeljId}")
    public List<CreateNatjecanjeDTO> getNatjecanjaByKorisnikId(@PathVariable Integer voditeljId){
        return natjecanjeService.getNatjecanjaByKorisnikId(voditeljId).stream().map(natjecanje -> {
            return new CreateNatjecanjeDTO(natjecanje.getNatjecanjeId(), natjecanje.getNazivNatjecanja(), natjecanje.getPocetakNatjecanja(), natjecanje.getKrajNatjecanja(), natjecanje.getVoditelj().getKorisnikId());
        }).toList();
    }

    @PostMapping("/update")
    public Natjecanje updateNatjecanje(@RequestBody CreateNatjecanjeDTO natjecanjeDTO){
        return natjecanjeService.updateNatjecanje(natjecanjeDTO);
    }
    @DeleteMapping("/delete/{natjecanjeId}")
    public void deleteNatjecanje(@PathVariable Integer natjecanjeId){
        natjecanjeService.deleteNatjecanje(natjecanjeId);
    }

}

