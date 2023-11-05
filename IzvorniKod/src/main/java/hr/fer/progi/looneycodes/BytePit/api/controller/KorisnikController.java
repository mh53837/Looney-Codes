package hr.fer.progi.looneycodes.BytePit.api.controller;

// local import
import hr.fer.progi.looneycodes.BytePit.service.KorisnikService;
import hr.fer.progi.looneycodes.BytePit.api.model.Korisnik;

// spring-boot imports
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.websocket.server.PathParam;

// java imports
import java.util.List;
import java.util.Optional;

/**
 * Kontroler koji sluzi kao pristup metodama vezane uz Korisnik entitet.
 * Ovdje su definirane adrese preko kojih mozemo kroz browser/postman pristupati nasim servisima.
 * @see KorisnikController
 * @see Korisnik
 * TODO: dodaj vise mapinga, testiraj
 */
@RestController
@RequestMapping("/user")
public class KorisnikController{
  @Autowired
  private KorisnikService korisnikService;

  @GetMapping("/all")
  public List<Korisnik> listAll(){
    return korisnikService.listAll();
  }

  @GetMapping("/{id}")
  public Optional<Korisnik> getKorisnik(@PathParam("id") int id){
    return korisnikService.fetch(id);
  }
}
