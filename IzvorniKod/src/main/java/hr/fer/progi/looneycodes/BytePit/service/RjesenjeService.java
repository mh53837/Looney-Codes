package hr.fer.progi.looneycodes.BytePit.service;


import hr.fer.progi.looneycodes.BytePit.api.model.Korisnik;
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

}