package hr.fer.progi.looneycodes.BytePit.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import hr.fer.progi.looneycodes.BytePit.api.controller.ZadatakDTO;
import hr.fer.progi.looneycodes.BytePit.api.model.Korisnik;
import hr.fer.progi.looneycodes.BytePit.api.model.Natjecanje;
import hr.fer.progi.looneycodes.BytePit.api.model.TestniPrimjer;
import hr.fer.progi.looneycodes.BytePit.api.model.Zadatak;

/**
 * Interface koji definira komunikaciju s bazom u odnosu na Zadatak entitete.
 */
@Service
public interface ZadatakService {
	  /**
	   * Vrati listu svih zadataka.
	   * @return lista svih zadataka ili null ako nema zadataka u sustavu
	   */
	  public List<ZadatakDTO> listAll();
	  /**
	   * Vrati listu svih javnih zadataka. Zadatak je javni ako mu je privatniZadatak = false
	   * @return lista svih javnih zadataka ili null ako nema zadataka u sustavu
	   */
	  public List<ZadatakDTO> listAllJavniZadatak();
	  /**
	   * Vrati listu svih zadataka s jednog natjecanja.
	   * @return lista svih zadataka s jednog natjecanja ili null ako nema zadataka u sustavu
	   */
	  public List<ZadatakDTO> listAllZadaciNatjecanje(Natjecanje natjecanje);
	  /**
	   * Vrati listu svih zadataka jednog voditelja.
	   * @return lista svih zadataka jednog voditelja ili null ako nema zadataka u sustavu
	   */
	  public List<ZadatakDTO> listAllZadaciVoditelj(String voditeljId);
	  /**
	   * Vrati listu svih zadataka jednog voditelja.
	   * @return lista svih zadataka jednog voditelja ili null ako nema zadataka u sustavu
	   */
	  public List<ZadatakDTO> listAllJavniZadaciVoditelj(String voditeljId);
	  /**
	   * Vrati referencu na zadatak s zadanim id-em.
	   * @param Id id zadatka kojeg trazimo
	   * @return korisnik ako postoji, inace Optional bez zadane vrijednosti
	   */
	  public Zadatak fetch(Integer Id);
	  /**
	   * Stvori novi zadatak i spremi ga u bazu
	   * @param zadatak referenca na zadatak kojeg moramo staviti u bazu, (nema postavljeni id)
	   * @param korisnickoIme - ime voditelja koji stvara zadatak
	   * @return referenca na napravljenog korisnika s postavljenim id-em
	   * @exception IllegalArgumentException u slucaju da je frontend team krivo stvorio objekt
	   */
	  public Zadatak createZadatak(ZadatakDTO zadatak, String korisnickoIme);
	  /**
	   * Azuriraj podatke o zdadatku sa zadanim id-em
	 * @param id 
	   * @param zadatak instanca u kojoj su pohranjeni azurirani podaci
	   * @exception IllegalArgumentException u slucaju da je id nepostojeci
	   * @return referenca na instancu zadatak klase s novim zapisom iz baze
	   */
	  public Zadatak updateZadatak(Integer id, Zadatak zadatak);
	  
	  /**
	   * Izbriši zadatak iz baze.
	   * @param id zadatka koji se treba izbrisati iz baze
	   * @return true ako je uspješno, inače false
	   */
	  public boolean deleteZadatak(Integer id);
	  

	/**
	 * Metoda koja pronalazi sve potpuno riješene zadatke zadanog natjecatelja.
	 * @param natjecatelj - identifikator natjecatelja
	 * @return listu zadataka zadanog natjecatelja
	 */
	List<ZadatakDTO> findByNatjecateljAllSolved(Korisnik natjecatelj);

	}
