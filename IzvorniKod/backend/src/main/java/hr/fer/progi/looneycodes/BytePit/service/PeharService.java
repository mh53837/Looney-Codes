package hr.fer.progi.looneycodes.BytePit.service;

import hr.fer.progi.looneycodes.BytePit.api.controller.AddPeharDTO;
import hr.fer.progi.looneycodes.BytePit.api.controller.RegisterKorisnikDTO;
import hr.fer.progi.looneycodes.BytePit.api.model.Korisnik;
import hr.fer.progi.looneycodes.BytePit.api.model.Pehar;
import org.springframework.data.util.Pair;
import org.springframework.http.MediaType;
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

    /**
     * Stvori novi Pehar i spremi ga u bazu.
     * @param dto instanca AddPeharDTO objekta
     * @return referenca na napravljeni pehar s postavljenim id-em
     * @exception RequestDeniedException
     * @exception IllegalArgumentException
     * @see Pehar
     * @see AddPeharDTO
     */
    public Pehar createPehar(AddPeharDTO dto);

    /**
     * Dohvati sliku pehara
     * @param peharId pehara za kojeg trazimo sliku
     * @return Pair<byte[], MediaType> slika i njen tip
     */
    Pair<byte[], MediaType> getImage(Integer peharId);
}
