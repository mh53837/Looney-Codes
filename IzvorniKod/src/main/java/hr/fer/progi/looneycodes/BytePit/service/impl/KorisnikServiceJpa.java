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

public class KorisnikServiceJpa implements KorisnikService {
  @Autowired
  KorisnikService korisnikService;

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
}
