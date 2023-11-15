package hr.fer.progi.looneycodes.BytePit.api.controller;

import hr.fer.progi.looneycodes.BytePit.api.model.Korisnik;
import hr.fer.progi.looneycodes.BytePit.api.model.Rjesenje;
import hr.fer.progi.looneycodes.BytePit.api.model.TestniPrimjer;
import hr.fer.progi.looneycodes.BytePit.api.model.Zadatak;
import hr.fer.progi.looneycodes.BytePit.service.KorisnikService;
import hr.fer.progi.looneycodes.BytePit.service.RjesenjeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 *
 * Kontroler koji služi kao pristup metodama vezane uz Rjesenje entitete.
 * Ovdje su definirane adrese preko kojih mozemo kroz browser/postman pristupati našim servisima.
 *
 */

@RestController
@RequestMapping("/api/solutions")
public class RjesenjeController {
    @Autowired
    private RjesenjeService rjesenjeService;

    @Autowired
    private KorisnikService korisnikService;

    @GetMapping("/all")
    public List<Rjesenje> listAll() {
        return rjesenjeService.listAll();
    }

    @GetMapping("get/natjecatelj/{korisnickoIme}")
    public List<Rjesenje> getRjesenjeByKorisnikId(@PathVariable String korisnickoIme) {
        Optional<Korisnik> korisnik = korisnikService.getKorisnik(korisnickoIme);

        Korisnik natjecatelj = null;
        if (korisnik.isPresent()) {
            natjecatelj = korisnik.get();
        }

        return rjesenjeService.findByRjesenjeIdNatjecatelj(natjecatelj);
    }

}