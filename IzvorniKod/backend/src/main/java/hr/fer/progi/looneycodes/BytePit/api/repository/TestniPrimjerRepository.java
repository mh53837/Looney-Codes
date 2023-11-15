package hr.fer.progi.looneycodes.BytePit.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import hr.fer.progi.looneycodes.BytePit.api.model.Korisnik;
import hr.fer.progi.looneycodes.BytePit.api.model.Natjecanje;
import hr.fer.progi.looneycodes.BytePit.api.model.TestniPrimjer;
import hr.fer.progi.looneycodes.BytePit.api.model.TestniPrimjerKey;
import hr.fer.progi.looneycodes.BytePit.api.model.Zadatak;

public interface TestniPrimjerRepository extends JpaRepository<TestniPrimjer, TestniPrimjerKey> {
	/**
	 * Metoda koja pronalazi testne primjere jednoga zadatka
	 * @param zadatak - zadatak za koji se traže testni primjeri
	 * @return lista testnih zadataka
	 */

	List<TestniPrimjer> findByTestniPrimjerIdZadatak(Zadatak zadatak);
}
