package hr.fer.progi.looneycodes.BytePit.service;


import hr.fer.progi.looneycodes.BytePit.api.controller.EvaluationResultDTO;
import hr.fer.progi.looneycodes.BytePit.api.controller.SubmissionDTO;
import hr.fer.progi.looneycodes.BytePit.api.model.Korisnik;
import hr.fer.progi.looneycodes.BytePit.api.model.Rjesenje;
import hr.fer.progi.looneycodes.BytePit.api.model.TestniPrimjer;
import hr.fer.progi.looneycodes.BytePit.api.model.Zadatak;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Interface koji definira komunikaciju s bazom u odnosu na Rjesenje entitete.
 */

@Service
public interface RjesenjeService {
    /**
     * Vrati listu svih rješenja.
     * @return lista svih rješenja ili null ako nema rješenja u sustavu
     */
    public List<Rjesenje> listAll();

    /**
     * Vrati listu svih rješenja zadanog natjecatelja.
     * @param natjecatelj identifikator natjecatelja
     * @return lista svih rješenja zadanog natjecatelja ili null ako nema rješenja u sustavu
     */
    public List<Rjesenje> findByRjesenjeIdNatjecatelj(Korisnik natjecatelj);

    /**
     * Stvori novo Rjesenje.
     * @return stvoreno Rjesenje
     */
    public Rjesenje add(Rjesenje rjesenje);

    /**
     * Vrati evaluirano rješenje s brojem točnih odgovora.
     * @param dto - SubmissionDTO 
     * @return postotak točnih odgovora
     */
	public EvaluationResultDTO evaluate(SubmissionDTO dto);

}
