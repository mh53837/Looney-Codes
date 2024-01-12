package hr.fer.progi.looneycodes.BytePit.service.impl;

import hr.fer.progi.looneycodes.BytePit.api.controller.CreateNatjecanjeDTO;
import hr.fer.progi.looneycodes.BytePit.api.controller.RangDTO;
import hr.fer.progi.looneycodes.BytePit.api.model.*;
import hr.fer.progi.looneycodes.BytePit.api.repository.NatjecanjeRepository;
import hr.fer.progi.looneycodes.BytePit.service.KorisnikService;
import hr.fer.progi.looneycodes.BytePit.service.NatjecanjeService;
import hr.fer.progi.looneycodes.BytePit.service.RjesenjeService;
import hr.fer.progi.looneycodes.BytePit.service.ZadatakService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class NatjecanjeServiceJpa implements NatjecanjeService {
    @Autowired
    private KorisnikService korisnikService;

    @Autowired
    private ZadatakService zadatakService;

    @Autowired
    private NatjecanjeRepository natjecanjeRepo;

    @Autowired
    private RjesenjeService rjesenjeService;

    public Optional<Korisnik> validacija(String nazivNatjecanja, Timestamp pocetakNatjecanja, Timestamp krajNatjecanja, String korisnickoIme) {

        Assert.notNull(nazivNatjecanja, "Naziv natjecanja ne smije biti null!");
        Assert.notNull(pocetakNatjecanja, "Pocetak natjecanja ne smije biti null!");
        Assert.notNull(krajNatjecanja, "Kraj natjecanja ne smije biti null!");
        Assert.notNull(korisnickoIme, "Korisnicko ime voditelja ne smije biti null!");
        Assert.isTrue(pocetakNatjecanja.before(krajNatjecanja), "Pocetak natjecanja mora biti prije kraja natjecanja!");
        // Assert.isTrue(pocetakNatjecanja.after(Timestamp.from(Instant.now())), "Pocetak natjecanja mora biti u buducnosti!");
        Optional<Korisnik> korisnik = korisnikService.getKorisnik(korisnickoIme);
        Assert.isTrue(korisnik.isPresent(), "Korisnik s korisnickim imenom " + korisnickoIme + " ne postoji!");
        Assert.isTrue(korisnik.get().getUloga().equals(Uloga.VODITELJ), "Korisnik s korisnickim imenom " + korisnickoIme + " nije voditelj!");
        return korisnik;
    }

    @Override
    public Natjecanje createNatjecanje(CreateNatjecanjeDTO natjecanjeDTO) {
        Optional<Korisnik> korisnik = validacija(natjecanjeDTO.getNazivNatjecanja(), natjecanjeDTO.getPocetakNatjecanja(), natjecanjeDTO.getKrajNatjecanja(), natjecanjeDTO.getKorisnickoImeVoditelja());
        Natjecanje natjecanje = new Natjecanje(korisnik.get(), natjecanjeDTO.getNazivNatjecanja(), natjecanjeDTO.getPocetakNatjecanja(), natjecanjeDTO.getKrajNatjecanja());
        natjecanje = natjecanjeRepo.save(natjecanje);
        return natjecanje;
    }


    @Override
    public Natjecanje updateNatjecanje(CreateNatjecanjeDTO natjecanjeDTO) {
        Natjecanje natjecanje = natjecanjeRepo.findByNatjecanjeId(natjecanjeDTO.getNatjecanjeId());
        Assert.notNull(natjecanje, "Natjecanje s ID-em " + natjecanjeDTO.getNatjecanjeId() + " ne postoji!");
        Optional<Korisnik> korisnik = validacija(natjecanjeDTO.getNazivNatjecanja(), natjecanjeDTO.getPocetakNatjecanja(), natjecanjeDTO.getKrajNatjecanja(), natjecanjeDTO.getKorisnickoImeVoditelja());

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

    @Override
    public List<RangDTO> getRangList(Integer natjecanjeId) {
        Natjecanje natjecanje = natjecanjeRepo.findByNatjecanjeId(natjecanjeId);
        Assert.notNull(natjecanje, "Natjecanje s ID-em " + natjecanjeId + " ne postoji!");
        Assert.isTrue(natjecanje.getKrajNatjecanja().before(new Timestamp(System.currentTimeMillis())), "Natjecanje nije zavrsilo!");


        Set<Zadatak> zadaci = natjecanjeRepo.findZadaciByNatjecanjeId(natjecanjeId);
        List<Rjesenje> rjesenjaNatjecanje = rjesenjeService.findByNatjecanjeId(natjecanjeId);

        //lista natjecatelja bi trebali biti atribut kod natjecanja, jer mozda netko nije nista predao?
        List<Korisnik> listaNatjecatelja = rjesenjaNatjecanje.stream().map(rjesenje -> rjesenje.getRjesenjeId().getNatjecatelj()).distinct().collect(Collectors.toList());

        List<RangDTO> rangList = listaNatjecatelja.stream().map(natjecatelj -> {
            Map<Integer, Double> zadatakBodovi = new HashMap<>();
            Double ukupniBodovi = 0.0;
            // ne radi rjesenjeService.findByNatjecateljAndNatjecanje, pa filtriramo ovde
            List<Rjesenje> rjesenja = rjesenjaNatjecanje.stream().filter(rjesenje -> rjesenje.getRjesenjeId().getNatjecatelj().getKorisnikId().equals(natjecatelj.getKorisnikId())).collect(Collectors.toList());
            for (Zadatak zadatak : zadaci) {
                Optional<Rjesenje> rjesenje = rjesenja.stream()
                		.filter(r -> r.getRjesenjeId().getZadatak().getZadatakId().equals(zadatak.getZadatakId()))
                		.max((r1, r2) -> Double.compare(r1.getBrojTocnihPrimjera(),  
                        r2.getBrojTocnihPrimjera()));
                if (rjesenje.isPresent()) {
                    zadatakBodovi.put(zadatak.getZadatakId(), rjesenje.get().getBrojTocnihPrimjera() * zadatak.getBrojBodova());
                } else {
                    zadatakBodovi.put(zadatak.getZadatakId(), 0.0);
                }
            }
            Duration vrijemeRjesavanja = Duration.between(natjecanje.getPocetakNatjecanja().toInstant(), rjesenja.stream().map(rjesenje -> rjesenje.getVrijemeOdgovora().toInstant()).max(Instant::compareTo).get());
            return new RangDTO(natjecatelj.getKorisnickoIme(), zadatakBodovi, vrijemeRjesavanja);
        }).collect(Collectors.toList());

        rangList.sort(Comparator.comparing(RangDTO::getUkupniBodovi).reversed().thenComparing(RangDTO::getVrijemeRjesavanja));
        int rang = 1;

        for (RangDTO rangDTO : rangList) {
            rangDTO.setRang(rang++);
        }

        /*
        int rang = 1;
        double prethodniBodovi;
        try {
             prethodniBodovi = rangList.get(0).getUkupniBodovi();
        } catch (IndexOutOfBoundsException e) {
            return rangList;
        }
        for (RangDTO rangDTO : rangList) {
            if (!(rangDTO.getUkupniBodovi() == (prethodniBodovi))) {
                rang++;
            }
            rangDTO.setRank(rang);
            prethodniBodovi = rangDTO.getUkupniBodovi();
        }*/

        return rangList;
    }
}

