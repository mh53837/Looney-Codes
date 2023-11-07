package hr.fer.progi.looneycodes.BytePit.api.controller;

// local import
import hr.fer.progi.looneycodes.BytePit.service.EmailService;
import hr.fer.progi.looneycodes.BytePit.service.KorisnikService;
import hr.fer.progi.looneycodes.BytePit.api.model.Korisnik;

// spring-boot imports
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// java imports
import java.util.List;
import java.util.Optional;

/**
 * Kontroler koji sluzi kao pristup metodama vezane uz Korisnik entitet.
 * Ovdje su definirane adrese preko kojih mozemo kroz browser/postman pristupati nasim servisima.
 * @see KorisnikService
 * @see Korisnik
 */
@RestController
@RequestMapping("/user")
public class KorisnikController{
  @Autowired
  private KorisnikService korisnikService;

  @Autowired
  private EmailService mailService;

  @Value("${BytePit.domain}")
  private String domena;
  /*
   * Izlistaj sve registrirane korisnike koji imaju potvrdeni account.
   * @return lista svih korisnika koji imaju atribut confirmedEmail = true
   */
  @GetMapping("/all")
  public List<Korisnik> listAll(){
    return korisnikService.listAllVerified();
  }

  /**
   * Vrati korisnika na temelju id-a
   * @return bilo koji korisnik koji je zapisan u bazi s zadanim id-em
   * @return Optional.empty() ako ne postoji korisnik s tim id-em
   */
  @GetMapping("/get/{id}")
  public Optional<Korisnik> getKorisnik(@PathVariable int id){
    return korisnikService.fetch(id);
  }

  /**
   * Registriraj novog korisnika, nakon cega on mora potvrditi registraciju
   * @return referenca na novi zapis korisnika u bazi
   */
  @PostMapping("/register")
  public Korisnik addKorisnik(@RequestBody Korisnik korisnik){
    // daj mu id
    korisnik = korisnikService.createKorisnik(korisnik);
    // salji mail za potvrdu registracije
    mailService.sendMail(korisnik.getEmail(), 
                          "DO NOT REPLY: Account confirmation for BytePit",
                          "Verify your BytePit account using this link: " +
                          domena + "/user/confirmEmail/" + korisnik.getKorisnikId());
    return korisnik;
  }

  @GetMapping("/confirmEmail/{id}")
  public Korisnik confirmEmail(@PathVariable int id){
    Optional<Korisnik> korisnik = korisnikService.fetch(id);

    if(korisnik.isEmpty())
      throw new IllegalArgumentException("Korisnik s id-em: " + korisnik.get().getKorisnikId() + " ne postoji!");
    if(korisnik.get().isConfirmedEmail())
      throw new IllegalStateException("Korisnik je vec potvrdio svoju email adresu!");

    Korisnik newKorisnik = korisnik.get();
    newKorisnik.setConfirmedEmail(true);
    return korisnikService.updateKorisnik(newKorisnik);
  }
  /**
   * azuriraj korisnicke podatke za odredenog korisnika
   * @return referenca na azurirani zapis u bazi
   */
  @PostMapping("/update")
  public Korisnik updateKorisnik(@RequestBody Korisnik korisnik){
    return korisnikService.updateKorisnik(korisnik);
  }
}
