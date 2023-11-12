package hr.fer.progi.looneycodes.BytePit.api.controller;

import hr.fer.progi.looneycodes.BytePit.api.model.Pehar;
import hr.fer.progi.looneycodes.BytePit.service.PeharService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *
 * Kontroler koji služi pristupu metodama vezanim uz Pehar entitet.
 * Ovdje su definirane adrese preko kojih mozemo kroz browser/postman pristupati našim servisima.
 */

@RestController
@RequestMapping("/trophies")
public class PeharController {

    @Autowired
    private PeharService peharService;

    /**
     * Ruta za ispis svih pehara.
     * @return lista svih pehara.
     */
    @GetMapping("/all")
    public List<Pehar> listAll() {
        return peharService.listAll();
    }

    /**
     * Ruta za ispis jednog pehara.
     * @return jedan pehar.
     */
    @GetMapping("/get/{id}")
    public Pehar oneTrophy(@PathVariable Integer id) {
        return peharService.oneTrophy(id);
    }

}
