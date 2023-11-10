package hr.fer.progi.looneycodes.BytePit.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hr.fer.progi.looneycodes.BytePit.api.model.Korisnik;
import hr.fer.progi.looneycodes.BytePit.api.model.TestniPrimjer;
import hr.fer.progi.looneycodes.BytePit.api.model.TestniPrimjerKey;
import hr.fer.progi.looneycodes.BytePit.api.model.Zadatak;
import hr.fer.progi.looneycodes.BytePit.service.KorisnikService;
import hr.fer.progi.looneycodes.BytePit.service.TestniPrimjerService;
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
	
	@Autowired
	private TestniPrimjerService testService;
	
	/**
	 * Ruta za ispis svih javnih zadataka.
	 * 
	 * @return lista svih javnih zadataka
	 */
	@GetMapping("/all")
	public List<Zadatak> listAll(){
	    return zadatakService.listAllJavniZadatak();
	}
	
	
	/**
	 * Ruta za ispis svih javnih zadataka.
	 * 
	 * @return lista svih javnih zadataka
	 */
	@GetMapping("/adminView")
	@Secured("ADMIN")
	public List<Zadatak> listAdmin(){
	    return zadatakService.listAll();
	}
	
	/**
	 * Ruta za stvaranje novog zadatka. Preko bodyja se šalju informacije o zadataku, a voditeljeve informacije preko login infa.
	 * 
	 * @param zadatak
	 * @param user
	 * @return
	 */
	@PostMapping("/new")
	@Secured("VODITELJ")
	public Zadatak addZadatak(@RequestBody Zadatak zadatak, @AuthenticationPrincipal UserDetails user){
		zadatak = zadatakService.createZadatak(zadatak, user.getUsername());
		return zadatak;
	}
	
	/**
	 * Ruta za dohvaćanje svih vlastitih zadataka jednog voditelja.
	 * 
	 * @param zadatak
	 * @param user
	 * @return
	 */
	@GetMapping("/my")
	@Secured("VODITELJ")
	public List<Zadatak> listAllFromOneVoditelj(@AuthenticationPrincipal UserDetails user){
		return zadatakService.listAllZadaciVoditelj(user.getUsername());
	}
	
	/**
	 * Ruta za dohvaćanje javnih zadataka jednog voditelja.
	 * 
	 * @param user
	 * @return
	 */
	@GetMapping("/author/{korisnickoIme}")
	public List<Zadatak> listAllFromOneVoditelj(@PathVariable String korisnickoIme){
		return zadatakService.listAllJavniZadaciVoditelj(korisnickoIme);
	}
	
	
	/**
	 * Ruta za dohvaćanje jednog zadatka.
	 * 
	 * @param user
	 * @return
	 */
	@GetMapping("/get/{id}")
	public Zadatak listAllFromOneVoditelj(@PathVariable Integer id){
		return zadatakService.fetch(id);
	}
	
	/**
	 * Ruta za dohvaćanje testnih primjera zadatka.
	 * 
	 * @param id identifikator zadatka 
	 * @return lista testnih primjera
	 */
	@GetMapping("/get/{id}/tests")
	public List<TestniPrimjer> listTests(@PathVariable Integer id){
		Zadatak zadatak = zadatakService.fetch(id);
		return testService.listAllByZadatak(zadatak);
	}
	
	/**
	 * Ruta za dodavanje novoga testnog primjera kod zadatka.
	 * 
	 * @param id identifikator zadatka 
	 * @return postavljeni testni primjer
	 */
	@PostMapping("/get/{id}/addTest")
	public TestniPrimjer addTest(@PathVariable Integer id, @RequestBody TestniPrimjer test){
		Zadatak zadatak = zadatakService.fetch(id);
		test.setTestniPrimjerId(new TestniPrimjerKey(null, zadatak));
		return testService.add(test);
	}
	
	//TODO dodati rute za zadatke po natjecanju nakon što se slože servisi i rute za natjecanje
}

