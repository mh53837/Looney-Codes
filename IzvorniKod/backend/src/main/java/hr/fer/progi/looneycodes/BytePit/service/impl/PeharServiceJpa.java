package hr.fer.progi.looneycodes.BytePit.service.impl;

import hr.fer.progi.looneycodes.BytePit.api.model.Korisnik;
import hr.fer.progi.looneycodes.BytePit.api.model.Pehar;
import hr.fer.progi.looneycodes.BytePit.api.model.Zadatak;
import hr.fer.progi.looneycodes.BytePit.api.repository.KorisnikRepository;
import hr.fer.progi.looneycodes.BytePit.api.repository.PeharRepository;
import hr.fer.progi.looneycodes.BytePit.service.PeharService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PeharServiceJpa implements PeharService {

    @Autowired
    PeharRepository peharRepository;

    @Autowired
    KorisnikRepository korisnikRepository;

    @Override
    public List<Pehar> listAll() {
        return peharRepository.findAllTrophies();
    }

    @Override
    public Pehar oneTrophy(Integer id) {
        Optional<Pehar> pehar = peharRepository.findById(id);
        if(pehar.isEmpty())
            throw new IllegalArgumentException("Pehar s id-em: " + id + "ne postoji!");
        return pehar.get();
    }

    @Override
    public List<Pehar> listAllFromOneNatjecatelj(String korisnickoIme) {
        Optional<Korisnik> natjecatelj = korisnikRepository.findByKorisnickoIme(korisnickoIme);
        if(natjecatelj.isEmpty())
            throw new IllegalArgumentException("Natjecatelj " + korisnickoIme + " ne postoji!");
        return peharRepository.findTrophiesByNatjecatelj(natjecatelj.get());
    }
}
