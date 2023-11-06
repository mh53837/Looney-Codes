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
  /**
   * Stvori novog Korisnika i spremi ga u bazu
   * @param korisnik referenca na korisnika kojeg moramo staviti u bazu, (nema postavljeni id)
   * @return referenca na napravljenog korisnika s postavljenim id-em
   * @see Korisnik
   * NOTE: metoda je tu samo radi testiranja, trebamo sloziti pravu registraciju kasnije
   */
  public Korisnik createKorisnik(Korisnik korisnik);
  /**
   * Azuriraj podatke o Korisniku sa zadanim id-em
   * @param korisnik instanca u kojoj su pohranjeni azurirani podaci
   * @return referenca na instancu Korisnik klase s novim zapisom iz baze
   * @see Korisnik
   */
  public Korisnik updateKorisnik(Korisnik korisnik);
}
