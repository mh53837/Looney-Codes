package hr.fer.progi.looneycodes.BytePit.api.repository;

// local imports
import hr.fer.progi.looneycodes.BytePit.api.model.Korisnik;

// spring-boot imports
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

// java imports
import java.util.Optional;
import java.util.List;

public interface KorisnikRepository extends JpaRepository<Korisnik, Integer> {
  /**
   * Metoda koja pronalazi korisnike po korisnickom imenu (posto je ono unique)
   * @param korisnickoIme ime koje zelimo naci u bazi
   * @return Optional<Korisnik> koji ima vrijednost samo ako smo ga nasli u bazi
   * @see Korisnik
   */
  Optional<Korisnik> findByKorisnickoIme(String korisnickoIme);
  /**
   * Metoda koja pronalazi sve korisnike koji imaju potvrdeni racun.
   * @return lista korisnika koji imaju potvrdeni racun
   * @see Korisnik
   */
  @Query("SELECT k FROM Korisnik k WHERE k.confirmedEmail = TRUE")
  List<Korisnik> findAllVerified();
  /**
   * Metoda koja pronalazi korisnike po emailu
   * @param email email adresa za koju zelimo naci korisnicki racun
   * @return Optional<Korisnik> koji je Optional.empty() ukoliko nema rezultata
   * @see Korisink
   */
  Optional<Korisnik> findByEmail(String email);
}
