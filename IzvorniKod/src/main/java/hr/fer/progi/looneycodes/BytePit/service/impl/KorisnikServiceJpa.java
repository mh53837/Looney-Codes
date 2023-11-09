package hr.fer.progi.looneycodes.BytePit.service.impl;

// local imports
import hr.fer.progi.looneycodes.BytePit.api.repository.KorisnikRepository;
import hr.fer.progi.looneycodes.BytePit.service.KorisnikService;
import hr.fer.progi.looneycodes.BytePit.service.RequestDeniedException;
import hr.fer.progi.looneycodes.BytePit.api.controller.KorisnikInfoDTO;
import hr.fer.progi.looneycodes.BytePit.api.controller.RegisterKorisnikDTO;
import hr.fer.progi.looneycodes.BytePit.api.model.Korisnik;
import hr.fer.progi.looneycodes.BytePit.api.model.Uloga;

// java imports
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.sql.Timestamp;
import java.time.Instant;

// spring-boot imports
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class KorisnikServiceJpa implements KorisnikService {
  @Autowired
  KorisnikRepository korisnikRepo;

  @Override
  public List<KorisnikInfoDTO> listAllVerified(){
    return korisnikRepo.findAllVerified()
                       .stream().map(korisnik -> new KorisnikInfoDTO(korisnik))
                       .collect(Collectors.toList());
  }
  @Override
  public List<KorisnikInfoDTO> listAllRequested(){
    return korisnikRepo.findAllRequested()
                       .stream().map(korisnik -> new KorisnikInfoDTO(korisnik))
                       .collect(Collectors.toList());
  }
  @Override
  public Optional<Korisnik> fetch(int id){
    return korisnikRepo.findById(id);
  }
  @Override
  public Korisnik createKorisnik(RegisterKorisnikDTO dto){
    Korisnik korisnik = new Korisnik(dto);
    validate(korisnik);
    // ako je korisnicko ime zauzeto, baci iznimku
    if(!korisnikRepo.findByKorisnickoIme(korisnik.getKorisnickoIme()).isEmpty())
      throw new RequestDeniedException("Korisnik s korisnickim imenom: " + korisnik.getKorisnickoIme() + " vec postoji!");
    if(!korisnikRepo.findByEmail(korisnik.getEmail()).isEmpty())
      throw new RequestDeniedException("Korisnik s emailom: " +
                                  korisnik.getEmail() + " vec postoji");

    Assert.notNull(korisnik.getRequestedUloga(), "Korisnik nije zatrazio ulogu!");
      
    // na pocetku je natjecatelj bez obzira na zatrazene uloge
    korisnik.setUloga(Uloga.NATJECATELJ);
    // treba potvrditi maila
    korisnik.setConfirmedEmail(false);
    // id nastaje automatski, timestamp se generira
    korisnik.setVrijemeRegistracije(Timestamp.from(Instant.now()));
    // hashiraj sifru
    korisnik = hashPass(korisnik);
    return korisnikRepo.save(korisnik);
  }
  @Override
  public Korisnik updateKorisnik(RegisterKorisnikDTO dto){
    String korisnickoIme = Objects.requireNonNull(dto.getKorisnickoIme());

    Korisnik stariKorisnik = korisnikRepo.findByKorisnickoIme(korisnickoIme)
                                         .orElseThrow(()
                                           -> new IllegalArgumentException("Korisnik s korisnickim imenom: " 
                                                                            + korisnickoIme + " ne postoji!"));

    Korisnik korisnik = new Korisnik(stariKorisnik, dto);
    validate(korisnik);
    if(!stariKorisnik
          .getLozinka().equals(
              korisnik.getLozinka()
              )
        )
      korisnik = hashPass(korisnik);
    return korisnikRepo.save(korisnik);
  }
  @Override
  public Optional<String> getPassHash(String username){
    Optional<Korisnik> korisnik = korisnikRepo.findByKorisnickoIme(username);

    return korisnik.isEmpty()? Optional.empty() : Optional.of(korisnik.get().getLozinka());
  }
  @Override
  public Optional<Uloga> getRole(String username){
    Optional<Korisnik> korisnik = korisnikRepo.findByKorisnickoIme(username);

    return korisnik.isEmpty()? Optional.empty() : Optional.of(korisnik.get().getUloga());
  }
  @Override
  public boolean confirmMail(Korisnik korisnik){
    if(korisnik == null)
      return false;

    korisnik.setConfirmedEmail(true);
    korisnikRepo.save(korisnik);
    return true;
  }
  @Override
  public boolean confirmRequest(Korisnik korisnik){
    if(korisnik == null)
      return false;

    korisnik.setUloga(korisnik.getRequestedUloga());
    korisnikRepo.save(korisnik);
    return true;
  }

  /**
   * privatna metoda koja sluzi za validaciju korisnika prije umetanja/updateanja u bazu
   * @exception IllegalArgumentException
   * @exception IllegalStateException
   */
  private void validate(Korisnik korisnik){
    Assert.notNull(korisnik, "User object reference must be given");
    Assert.notNull(korisnik.getLozinka(), "Lozinka ne moze biti null!");
    Assert.notNull(korisnik.getKorisnickoIme(), "Korisnicko ime ne moze biti null!");
    Assert.notNull(korisnik.getEmail(), "Email ne moze biti null!");
  }
  /**
   * Metoda koja hashira sifru
   */
  private Korisnik hashPass(Korisnik korisnik){
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    korisnik.setLozinka(encoder.encode(korisnik.getLozinka()));
    return korisnik;
  }
}
