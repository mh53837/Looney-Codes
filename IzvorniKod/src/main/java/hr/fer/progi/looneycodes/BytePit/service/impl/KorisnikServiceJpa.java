package hr.fer.progi.looneycodes.BytePit.service.impl;

// local imports
import hr.fer.progi.looneycodes.BytePit.api.repository.KorisnikRepository;
import hr.fer.progi.looneycodes.BytePit.service.KorisnikService;
import hr.fer.progi.looneycodes.BytePit.api.model.Korisnik;

// java imports
import java.util.List;
import java.util.Optional;

// spring-boot imports
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class KorisnikServiceJpa implements KorisnikService {
  @Autowired
  KorisnikRepository korisnikRepo;

  @Override
  public List<Korisnik> listAll(){
    return korisnikRepo.findAll();
  }
  @Override
  public Optional<Korisnik> fetch(int id){
    return korisnikRepo.findById(id);
  }
  @Override
  public Korisnik createKorisnik(Korisnik korisnik){
    validate(korisnik);
    // id novog korisnika mora biti null, inace baci iznimku
    Assert.isNull(korisnik.getKorisnikId(), "Id for new user must be null!");
    // ako je korisnicko ime zauzeto, baci iznimku
    if(!korisnikRepo.findByKorisnickoIme(korisnik.getKorisnickoIme()).isEmpty())
      throw new IllegalArgumentException("Korisnik s korisnickim imenom: " + korisnik.getKorisnickoIme() + " vec postoji!");

    // id nastaje automatski
    return korisnikRepo.save(korisnik);
  }


  /**
   * privatna metoda koja sluzi za validaciju korisnika prije umetanja/updateanja u bazu
   * @exception IllegalArgumentException
   * @exception IllegalStateException
   */
  private void validate(Korisnik korisnik){
    Assert.notNull(korisnik, "User object reference must be given");
    Assert.isTrue(korisnik.getUloga().getNazivUloge() == "ADMIN"
                || korisnik.getUloga().getNazivUloge() == "VODITELJ"
                || korisnik.getUloga().getNazivUloge() == "NATJECATELJ",
                "Zadana uloga ne postoji u sustavu!");
  }
}
