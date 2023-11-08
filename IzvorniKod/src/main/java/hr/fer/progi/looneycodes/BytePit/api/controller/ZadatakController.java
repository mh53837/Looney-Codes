package hr.fer.progi.looneycodes.BytePit.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hr.fer.progi.looneycodes.BytePit.api.model.Korisnik;
import hr.fer.progi.looneycodes.BytePit.api.model.Zadatak;
import hr.fer.progi.looneycodes.BytePit.service.KorisnikService;
import hr.fer.progi.looneycodes.BytePit.service.ZadatakService;

/**
 * 
 * Kontroler koji služi kao pristup metodama vezane uz Zadatak entitet.
 * Ovdje su definirane adrese preko kojih mozemo kroz browser/postman pristupati našim servisima.
 * 
 */
@RestController
@RequestMapping("/problems")
public class ZadatakController {
	@Autowired
	private ZadatakService zadatakService;
	
	@GetMapping("/all")
	public List<Zadatak> listAll(){
	    return zadatakService.listAllJavniZadatak();
	}
	
	@PostMapping("/new")
	public Zadatak addKorisnik(@RequestBody Zadatak zadatak){
		zadatak = zadatakService.createZadatak(zadatak);
		return zadatak;
	}
	
}
