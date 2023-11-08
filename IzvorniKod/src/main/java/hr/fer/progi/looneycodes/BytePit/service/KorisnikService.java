package hr.fer.progi.looneycodes.BytePit.service;

// local import
import hr.fer.progi.looneycodes.BytePit.api.model.Korisnik;
import hr.fer.progi.looneycodes.BytePit.api.model.Uloga;

// spring-boot imports
import org.springframework.stereotype.Service;

// java imports
import java.util.List;
import java.util.Optional;

/**
 * Interface koji definira komunikaciju s bazom u odnosu na Korisnik entitete.
 */
@Service
public interface KorisnikService {
  /**
   * Vrati listu svih korisnika s potvrdenim racunom.
   * @return lista svih korisnika ili null ako nitko nije u sustavu
   */
  public List<Korisnik> listAllVerified();
  /**
   * Vrati listu svih korisnika koji traze promijenu uloga
   * @return lista svih korisnika s atributima: uloga != requestedUloga
   */
  public List<Korisnik> listAllRequested();
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
   * @exception RequestDeniedException u slucaju da je email ili username vec zauzet 
   * @exception IllegalArgumentException u slucaju da je frontend team krivo stvorio objekt :(
   * @see Korisnik
   * NOTE: metoda je tu samo radi testiranja, trebamo sloziti pravu registraciju kasnije
   */
  public Korisnik createKorisnik(Korisnik korisnik);
  /**
   * Azuriraj podatke o Korisniku sa zadanim id-em
   * @param korisnik instanca u kojoj su pohranjeni azurirani podaci
   * @exception IllegalArgumentException u slucaju da je id nepostojeci ili da pokusavamo mijenjati uloge, a.k.a. frontend team je kriv
   * @return referenca na instancu Korisnik klase s novim zapisom iz baze
   * @see Korisnik
   */
  public Korisnik updateKorisnik(Korisnik korisnik);
  /**
   * Dohvati hash sifre prilikom registracije
   * @param username korisnicko ime za koju trazimo sifru
   * @return sifra ili Optional.empty() ako nema username-a u bazi
   */
  public Optional<String> getPassHash(String username);
  /**
   * Dohvati ulogu korisnika
   * @param username korisnicko ime za koje trazimo ulogu
   * @return uloga ili Optional.empty() ako nema username-a u bazi
   */
  public Optional<Uloga> getRole(String username);
}
