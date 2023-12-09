package hr.fer.progi.looneycodes.BytePit.service;


import hr.fer.progi.looneycodes.BytePit.api.controller.EvaluationResultDTO;
import hr.fer.progi.looneycodes.BytePit.api.controller.SubmissionDTO;
import hr.fer.progi.looneycodes.BytePit.api.model.Korisnik;
import hr.fer.progi.looneycodes.BytePit.api.model.Rjesenje;
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
     * Parametri su EvaluationResultDTO koji predstavlja rezultate evaluacije te id-evi korisnika / zadatka
     *
     * @param dto
     * @param korisnickoIme
     * @param zadatakId
     * @param programskiKod
     * @return stvoreno Rjesenje
     */
    public Rjesenje add(EvaluationResultDTO dto, String korisnickoIme, Integer zadatakId, String programskiKod);

    /**
     * Vrati evaluirano rješenje s brojem točnih odgovora.
     * @param dto - SubmissionDTO 
     * @return EvaluationResultDTO koji sadrzi rezultate obrade rjesenja
     *
     * @see EvaluationResultDTO
     */
	public EvaluationResultDTO evaluate(SubmissionDTO dto);

}
