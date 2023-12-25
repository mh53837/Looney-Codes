package hr.fer.progi.looneycodes.BytePit.service.impl;

import hr.fer.progi.looneycodes.BytePit.api.controller.CreateNatjecanjeDTO;
import hr.fer.progi.looneycodes.BytePit.api.model.Korisnik;
import hr.fer.progi.looneycodes.BytePit.api.model.Natjecanje;
import hr.fer.progi.looneycodes.BytePit.api.model.Uloga;
import hr.fer.progi.looneycodes.BytePit.api.model.Zadatak;
import hr.fer.progi.looneycodes.BytePit.api.repository.NatjecanjeRepository;
import hr.fer.progi.looneycodes.BytePit.service.KorisnikService;
import hr.fer.progi.looneycodes.BytePit.service.NatjecanjeService;
import hr.fer.progi.looneycodes.BytePit.service.ZadatakService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class NatjecanjeServiceJpa implements NatjecanjeService {
    @Autowired
    private KorisnikService korisnikService;

    @Autowired
    private ZadatakService zadatakService;

    @Autowired
    private NatjecanjeRepository natjecanjeRepo;

    public Optional<Korisnik> validacija(String nazivNatjecanja, Timestamp pocetakNatjecanja, Timestamp krajNatjecanja, Integer korisnikId) {
        Assert.notNull(nazivNatjecanja, "Naziv natjecanja ne smije biti null!");
        Assert.notNull(pocetakNatjecanja, "Pocetak natjecanja ne smije biti null!");
        Assert.notNull(krajNatjecanja, "Kraj natjecanja ne smije biti null!");
        Assert.notNull(korisnikId, "ID voditelja ne smije biti null!");
        Assert.isTrue(pocetakNatjecanja.before(krajNatjecanja), "Pocetak natjecanja mora biti prije kraja natjecanja!");
        // Assert.isTrue(pocetakNatjecanja.after(Timestamp.from(Instant.now())), "Pocetak natjecanja mora biti u buducnosti!");
        Optional<Korisnik> korisnik = korisnikService.fetch(korisnikId);
        Assert.isTrue(korisnik.isPresent(), "Korisnik s ID-em " + korisnikId + " ne postoji!");
        Assert.isTrue(korisnik.get().getUloga().equals(Uloga.VODITELJ), "Korisnik s ID-em " + korisnikId + " nije voditelj!");
        return korisnik;
    }

    @Override
    public Natjecanje createNatjecanje(CreateNatjecanjeDTO natjecanjeDTO) {
        Optional<Korisnik> korisnik = validacija(natjecanjeDTO.getNazivNatjecanja(), natjecanjeDTO.getPocetakNatjecanja(), natjecanjeDTO.getKrajNatjecanja(), natjecanjeDTO.getVoditeljId());
        Natjecanje natjecanje = new Natjecanje(korisnik.get(), natjecanjeDTO.getNazivNatjecanja(), natjecanjeDTO.getPocetakNatjecanja(), natjecanjeDTO.getKrajNatjecanja());
        natjecanje = natjecanjeRepo.save(natjecanje);
        return natjecanje;
    }


    @Override
    public Natjecanje updateNatjecanje(CreateNatjecanjeDTO natjecanjeDTO) {
        Natjecanje natjecanje = natjecanjeRepo.findByNatjecanjeId(natjecanjeDTO.getNatjecanjeId());
        Assert.notNull(natjecanje, "Natjecanje s ID-em " + natjecanjeDTO.getNatjecanjeId() + " ne postoji!");
        Optional<Korisnik> korisnik = validacija(natjecanjeDTO.getNazivNatjecanja(), natjecanjeDTO.getPocetakNatjecanja(), natjecanjeDTO.getKrajNatjecanja(), natjecanjeDTO.getVoditeljId());
        natjecanje.setNazivNatjecanja(natjecanjeDTO.getNazivNatjecanja());
        natjecanje.setPocetakNatjecanja(natjecanjeDTO.getPocetakNatjecanja());
        natjecanje.setKrajNatjecanja(natjecanjeDTO.getKrajNatjecanja());
        natjecanje.setVoditelj(korisnik.get());
        natjecanje = natjecanjeRepo.save(natjecanje);
        return natjecanje;
    }

    @Override
    public Natjecanje getNatjecanje(Integer natjecanjeId) {
        return natjecanjeRepo.findByNatjecanjeId(natjecanjeId);
    }

    @Override
    public List<Natjecanje> listAllNatjecanja() {
        return natjecanjeRepo.findAll();

    }

    @Override
    public List<Natjecanje> getNatjecanjaByKorisnikId(Integer korisnikId) {
        return natjecanjeRepo.findByKorisnikId(korisnikId);
    }
    @Override
    public List<Natjecanje> getUpcomingNatjecanja() {
        return natjecanjeRepo.findUpcomingNatjecanja();
    }

    @Override
    public List<Natjecanje> getOngoingNatjecanja() {
        return natjecanjeRepo.findOngoingNatjecanja();
    }

    @Override
    public List<Natjecanje> getFinishedNatjecanja() {
        return natjecanjeRepo.findFinishedNatjecanja();
    }

    @Override
    public Set<Zadatak> getZadaciByNatjecanjeId(Integer natjecanjeId) {
        return natjecanjeRepo.findZadaciByNatjecanjeId(natjecanjeId);
    }

    @Override
    public void addZadatakToNatjecanje(Integer natjecanjeId, Integer zadatakId) {
        Assert.notNull(zadatakId, "ID zadatka ne smije biti null!");

        Natjecanje natjecanje = natjecanjeRepo.findByNatjecanjeId(natjecanjeId);
        Zadatak zadatak = zadatakService.fetch(zadatakId);

        Assert.notNull(zadatak, "Zadatak s ID-em " + zadatakId + " ne postoji!");
        //za ovo nisam siguran jel treba vrijediti
        Assert.isTrue(natjecanje.getVoditelj().getKorisnikId().equals(zadatak.getVoditelj().getKorisnikId()), "Zadatak i natjecanje ne pripadaju istom voditelju!");

        Set<Zadatak> zadaci = natjecanje.getZadaci();
        zadaci.add(zadatak);
        natjecanje.setZadaci(zadaci);
        natjecanjeRepo.save(natjecanje);

    }

    @Override
    public void removeZadatakFromNatjecanje(Integer natjecanjeId, Integer zadatakId) {
        Assert.notNull(zadatakId, "ID zadatka ne smije biti null!");

        Natjecanje natjecanje = natjecanjeRepo.findByNatjecanjeId(natjecanjeId);
        Zadatak zadatak = zadatakService.fetch(zadatakId);

        Assert.notNull(zadatak, "Zadatak s ID-em " + zadatakId + " ne postoji!");

        Set<Zadatak> zadaci = natjecanje.getZadaci();
        zadaci.remove(zadatak);
        natjecanje.setZadaci(zadaci);
        natjecanjeRepo.save(natjecanje);

    }
}
