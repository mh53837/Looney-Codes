package hr.fer.progi.looneycodes.BytePit.service.impl;

// local imports
import hr.fer.progi.looneycodes.BytePit.api.repository.KorisnikRepository;
import hr.fer.progi.looneycodes.BytePit.service.KorisnikService;
import hr.fer.progi.looneycodes.BytePit.api.model.Korisnik;
import hr.fer.progi.looneycodes.BytePit.api.model.Uloga;

// java imports
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.sql.Timestamp;
import java.time.Instant;

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

    // id nastaje automatski, timestamp se generira
    korisnik.setConfirmedEmail(false);
    korisnik.setVrijemeRegistracije(Timestamp.from(Instant.now()));
    return korisnikRepo.save(korisnik);
  }
  @Override
  public Korisnik updateKorisnik(Korisnik korisnik){
    validate(korisnik);
    int idKorisnika = Objects.requireNonNull(korisnik.getKorisnikId());

    Optional<Korisnik> stariKorisnik = korisnikRepo.findById(idKorisnika);
    if(stariKorisnik.isEmpty())
      throw new IllegalArgumentException("Korisnik s id-em: " + Integer.toString(idKorisnika) + " ne postoji!");

    // ne smijemo mijenjati vrstu accounta!!!
    if(stariKorisnik.get().getUloga() != korisnik.getUloga())
      throw new IllegalArgumentException("Korisnik ne smije mijenjati uloge!");

    return korisnikRepo.save(korisnik);
  }

  /**
   * privatna metoda koja sluzi za validaciju korisnika prije umetanja/updateanja u bazu
   * @exception IllegalArgumentException
   * @exception IllegalStateException
   */
  private void validate(Korisnik korisnik){
    Assert.notNull(korisnik, "User object reference must be given");
    Assert.notNull(korisnik.getUloga(), "Uloga ne moze biti null!");
    Assert.notNull(korisnik.getLozinka(), "Lozinka ne moze biti null!");
    Assert.notNull(korisnik.getKorisnickoIme(), "Korisnicko ime ne moze biti null!");
    Assert.isTrue(korisnik.getUloga() == Uloga.ADMIN
                || korisnik.getUloga() == Uloga.VODITELJ
                || korisnik.getUloga() == Uloga.NATJECATELJ,
                "Zadana uloga ne postoji u sustavu!");
  }
}
