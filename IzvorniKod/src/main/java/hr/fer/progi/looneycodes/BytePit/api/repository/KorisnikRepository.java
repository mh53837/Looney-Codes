package hr.fer.progi.looneycodes.BytePit.api.repository;

// local imports
import hr.fer.progi.looneycodes.BytePit.api.model.Korisnik;

// spring-boot imports
import org.springframework.data.jpa.repository.JpaRepository;

// java imports
import java.util.Optional;

public interface KorisnikRepository extends JpaRepository<Korisnik, Integer> {
  /**
   * TODO: testiraj i dodaj servis
   */
  Optional<Korisnik> findByUsername(String username);
}
