package hr.fer.progi.looneycodes.BytePit.api.controller;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import hr.fer.progi.looneycodes.BytePit.api.model.Korisnik;
import hr.fer.progi.looneycodes.BytePit.api.repository.KorisnikRepository;
import hr.fer.progi.looneycodes.BytePit.service.KorisnikService;
import hr.fer.progi.looneycodes.BytePit.service.RequestDeniedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hr.fer.progi.looneycodes.BytePit.api.model.TestniPrimjer;
import hr.fer.progi.looneycodes.BytePit.api.model.TestniPrimjerKey;
import hr.fer.progi.looneycodes.BytePit.api.model.Zadatak;
import hr.fer.progi.looneycodes.BytePit.service.TestniPrimjerService;
import hr.fer.progi.looneycodes.BytePit.service.ZadatakService;

/**
 * 
 * Kontroler koji služi kao pristup metodama vezane uz Zadatak entitet.
 * Ovdje su definirane adrese preko kojih mozemo kroz browser/postman pristupati našim servisima.
 * 
 */
@RestController
@RequestMapping("/api/problems")
public class ZadatakController {
	@Autowired
	private ZadatakService zadatakService;
	
	@Autowired
	private TestniPrimjerService testService;

	@Autowired
	private KorisnikService korisnikService;
	
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
	 * Ruta za ispis svih zadataka.
	 * 
	 * @return lista svih zadataka
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
	 * @param id
	 * @return
	 */
	@GetMapping("/get/{id}")
	public Zadatak listAllFromOneVoditelj(@PathVariable Integer id){
		return zadatakService.fetch(id);
	}
	
	/**
	 * Ruta za brisanje jednog zadatka.
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/delete/{id}")
	public boolean deleteZadatakById(@PathVariable Integer id){
		return zadatakService.deleteZadatak(id);
	}
	
	/**
	 * Ruta za dohvaćanje testnih primjera zadatka.
	 * 
	 * @param id identifikator zadatka 
	 * @return lista testnih primjera
	 */
	@GetMapping("/get/{id}/tests")
  @Secured({"ADMIN", "VODITELJ"})
	public List<TestniPrimjer> listTests(@PathVariable Integer id){
		Zadatak zadatak = zadatakService.fetch(id);
		return testService.listAllByZadatak(zadatak);
	}

	/**
	 * Ruta za dohvaćanje svih riješenih zadataka zadanog natjecatelja
	 * @param id identifikator natjecatelja
	 * @return lista zadataka
	 */
	@GetMapping("/get/{korisnickoIme}/allSolvedTasks")
	public List<Zadatak> listAllSolvedTasksFromOneNatjecatelj(@PathVariable String korisnickoIme){
		Optional<Korisnik> korisnik = korisnikService.getKorisnik(korisnickoIme);

		if (!korisnik.isPresent()) {
			throw new RequestDeniedException("Korisnik ne postoji!");
		}

		return zadatakService.findByNatjecateljAllSolved(korisnik.get());
	}
	
	/**
	 * Ruta za dodavanje novoga testnog primjera kod zadatka.
	 * 
	 * @param id identifikator zadatka 
	 * @return postavljeni testni primjer
	 */
	@PostMapping("/get/{id}/addTest")
	@Secured({"ADMIN", "VODITELJ"})
	public TestniPrimjer addTest(@PathVariable Integer id, @RequestBody TestniPrimjer test, @AuthenticationPrincipal UserDetails user){
    if(Objects.isNull(user))
      throw new AccessDeniedException("You must be logged in for that!");

		Zadatak zadatak = zadatakService.fetch(id);

    if(!user.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))
        && !zadatak.getVoditelj().getIme().equals(user.getUsername()))
      throw new AccessDeniedException("Morate biti autor zadatka ili admin da biste dodali testove!");

		test.setTestniPrimjerId(new TestniPrimjerKey(null, zadatak));
		return testService.add(test);
	}
	
	/**
	   * Azuriraj podatke za odredeni zadatak.
	   * @param id salje se kao path variable
	   * @param dto samo atributi koje zelimo mijenjati se postave u dto, ostalo se ignorira automatski
	   * @param user trenutno autentificirani korisnik, radi sigurnosti provjeravamo da ne mijenja tude zadatke
	   * @exception IllegalArgumentException u slucaju da pokusavamo mijenjati tude podatke (a da nismo ADMIN!)
	   * @exception AccessDeniedException u slucaju da nismo ulogirani
	   * @return referenca na azurirani zapis u bazi
	   */
	  @PostMapping("/update/{id}")
	  @Secured({"VODITELJ", "ADMIN"})
	  public Zadatak updateKorisnik(@PathVariable Integer id, @RequestBody Zadatak dto, @AuthenticationPrincipal UserDetails user){
	    if(Objects.isNull(user))
	      throw new AccessDeniedException("You must be logged in for that!");
	    	    
	    if(!user.getUsername().equals(zadatakService.fetch(id))
	        && !user.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN")))
	      throw new IllegalStateException("Krivi korisnik!");

	    return zadatakService.updateZadatak(id, dto);
	  }
	
	//TODO dodati rute za zadatke po natjecanju nakon što se slože servisi i rute za natjecanje
}

