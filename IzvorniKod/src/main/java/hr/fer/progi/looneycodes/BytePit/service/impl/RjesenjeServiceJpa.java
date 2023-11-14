package hr.fer.progi.looneycodes.BytePit.service.impl;

import hr.fer.progi.looneycodes.BytePit.api.model.Korisnik;
import hr.fer.progi.looneycodes.BytePit.api.model.Rjesenje;
import hr.fer.progi.looneycodes.BytePit.api.repository.RjesenjeRepository;
import hr.fer.progi.looneycodes.BytePit.service.RjesenjeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RjesenjeServiceJpa implements RjesenjeService {

    @Autowired
    RjesenjeRepository rjesenjeRepository;

    @Override
    public List<Rjesenje> listAll() {
        return rjesenjeRepository.findAll();
    }

    @Override
    public List<Rjesenje> findByRjesenjeIdNatjecatelj(Korisnik natjecatelj) {
        return rjesenjeRepository.findByRjesenjeIdNatjecatelj(natjecatelj);
    }
}