package hr.fer.progi.looneycodes.BytePit.service.impl;

import hr.fer.progi.looneycodes.BytePit.api.controller.AddPeharDTO;
import hr.fer.progi.looneycodes.BytePit.api.model.*;
import hr.fer.progi.looneycodes.BytePit.api.repository.KorisnikRepository;
import hr.fer.progi.looneycodes.BytePit.api.repository.NatjecanjeRepository;
import hr.fer.progi.looneycodes.BytePit.api.repository.PeharRepository;
import hr.fer.progi.looneycodes.BytePit.service.KorisnikService;
import hr.fer.progi.looneycodes.BytePit.service.PeharService;
import hr.fer.progi.looneycodes.BytePit.service.RequestDeniedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class PeharServiceJpa implements PeharService {

    @Autowired
    NatjecanjeRepository natjecanjeRepository;

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

    /**
     * privatna metoda koja sluzi za validaciju korisnika prije umetanja/updateanja u bazu
     * @exception IllegalArgumentException
     * @exception IllegalStateException
     */
    private void validate(Pehar pehar){
        Assert.notNull(pehar, "User object reference must be given");
    }

    @Override
    public Pehar createPehar(AddPeharDTO dto) {
        //Optional<Korisnik> korisnik = korisnikRepository.findByKorisnickoIme(dto.getKorisnickoImeNatjecatelja());
        Natjecanje natjecanje = natjecanjeRepository.findByNatjecanjeId(dto.getNatjecanjeId());
        Pehar pehar = new Pehar(null, natjecanje, dto.getMjesto(), dto.getSlikaPehara());
        validate(pehar);
        return peharRepository.save(pehar);
    }

    @Override
    public Pair<byte[], MediaType> getImage(Integer peharId) {
        Optional<Pehar> pehar = peharRepository.findById(peharId);
        Assert.isTrue(pehar.isPresent(), "Pehar s id: " + peharId + " ne postoji!");
        String path = pehar.get().getSlikaPehara();
        Assert.notNull(path, "Pehar nema sliku!");
        try {
            byte[] imageData = Files.readAllBytes(Paths.get(path));
            String fileExtension = path.substring(path.lastIndexOf('.') + 1);
            String contentType = "image/" + fileExtension.toLowerCase();
            MediaType mediaType = MediaType.parseMediaType(contentType);
            return Pair.of(imageData, mediaType);
        } catch (Exception e){
            throw new RequestDeniedException("Greska prilikom čitanja slike!");
        }
    }
}
