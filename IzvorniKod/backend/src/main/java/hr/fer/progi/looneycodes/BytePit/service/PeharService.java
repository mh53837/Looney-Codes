package hr.fer.progi.looneycodes.BytePit.service;

import hr.fer.progi.looneycodes.BytePit.api.model.Pehar;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Interface koji definira komunikaciju s bazom u odnosu na Pehar entitete.
 */
@Service
public interface PeharService {
    /**
     * Vrati listu svih pehara.
     * @return lista svih pehara ili null ako nema pehara u sustavu
     */
    public List<Pehar> listAll();

    /**
     * Vrati jedan pehar na temelju ID.
     * @param Id id pehara kojeg trazimo
     * @return pehar na temelju ID ili null ako nema tog pehara
     */
    public Pehar oneTrophy(Integer Id);

    /**
     * Vrati listu svih pehara jednog korisnika.
     * @return lista svih pehara jednog korisnika ili null ako nema pehara u sustavu
     */
    public List<Pehar> listAllFromOneNatjecatelj(String korisnickoIme);

}
