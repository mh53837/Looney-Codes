package hr.fer.progi.looneycodes.BytePit.service;

// local import
import hr.fer.progi.looneycodes.BytePit.api.model.Korisnik;

// spring-boot imports
import org.springframework.stereotype.Service;

// java imports
import java.util.List;
import java.util.Optional;

/**
 * Interface koji definira komunikaciju s bazom u odnosu na Korisnik entitete.
 * TODO: implementacija (model/jpa + service/impl mape s odgovarajucim metodama), dodavanje novih metoda
 */
@Service
public interface KorisnikService {
  /**
   * Vrati listu svih korisnika.
   * @return lista svih korisnika ili null ako nitko nije u sustavu
   */
  public List<Korisnik> listAll();
  /**
   * Vrati referencu na Korisnika s zadanim id-em.
   * @param Id id korisnika kojeg trazimo
   * @return korisnik ako postoji, inace Optional bez zadane vrijednosti (Optional.empty())
   * @see Optional
   */
  public Optional<Korisnik> fetch(int Id);
}
