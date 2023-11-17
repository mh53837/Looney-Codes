package hr.fer.progi.looneycodes.BytePit.service;

import java.util.List;

import org.springframework.stereotype.Service;

import hr.fer.progi.looneycodes.BytePit.api.model.TestniPrimjer;
import hr.fer.progi.looneycodes.BytePit.api.model.Zadatak;

/**
 * Interface koji definira komunikaciju s bazom u odnosu na TestniPrimjer entitete.
 */
@Service
public interface TestniPrimjerService {
	  /**
	   * Vrati listu svih testnih primjera za jedan zadatak.
	   * @return lista svih zadataka ili null ako nema zadataka u sustavu
	   */
	  
	  public List<TestniPrimjer> listAllByZadatak(Zadatak zadatak);
	  
	  /**
	   * Stvori novi TestniPrimjer.
	   * @return stvoreni TestniPrimjer
	   */
	  public TestniPrimjer add(TestniPrimjer test);
}
