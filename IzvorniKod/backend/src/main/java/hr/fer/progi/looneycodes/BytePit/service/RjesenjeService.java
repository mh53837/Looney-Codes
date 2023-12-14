package hr.fer.progi.looneycodes.BytePit.service;


import hr.fer.progi.looneycodes.BytePit.api.controller.EvaluationResultDTO;
import hr.fer.progi.looneycodes.BytePit.api.controller.SubmissionDTO;
import hr.fer.progi.looneycodes.BytePit.api.model.Korisnik;
import hr.fer.progi.looneycodes.BytePit.api.model.Natjecanje;
import hr.fer.progi.looneycodes.BytePit.api.model.Rjesenje;
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
     * Metoda koja pronalazi sva rješenja po natjecatelju i natjecanju.
     * @param natjecatelj - identifikator natjecatelja
     * @param natjecanje - identifikator natjecanja
     * @return lista rješenja zadanog natjecatelja u određenom natjecanju ili null ako nema rješenja u sustavu
     */
    public List<Rjesenje> findByNatjecateljAndNatjecanje(Korisnik natjecatelj, Natjecanje natjecanje);

    /**
     * Metoda koja pronalazi sva rješenja po zadatku i natjecanju.
     * @param natjecanje - identifikator natjecanja
     * @param zadatak - identifikator zadatka
     * @return lista rješenja zadanog zadatka u određenom natjecanju
     */
    public List<Rjesenje> findByNatjecanjeAndZadatak(Natjecanje natjecanje, Zadatak zadatak);

    /**
     * Metoda koja pronalazi sva rješenja po zadatku i natjecanju.
     * @param natjecatelj - identifikator natjecatelja
     * @param zadatak - identifikator zadatka
     * @return lista rješenja zadanog zadatka u određenom natjecanju
     */
    public List<Rjesenje> findByNatjecateljAndZadatak(Korisnik natjecatelj, Zadatak zadatak);

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
