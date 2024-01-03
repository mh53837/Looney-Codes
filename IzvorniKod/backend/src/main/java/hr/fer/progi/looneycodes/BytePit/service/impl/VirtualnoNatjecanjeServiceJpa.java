package hr.fer.progi.looneycodes.BytePit.service.impl;

import hr.fer.progi.looneycodes.BytePit.api.controller.RangDTO;
import hr.fer.progi.looneycodes.BytePit.api.controller.VirtualnoNatjecanjeDTO;
import hr.fer.progi.looneycodes.BytePit.api.model.*;
import hr.fer.progi.looneycodes.BytePit.api.repository.VirtualnoNatjecanjeRepository;
import hr.fer.progi.looneycodes.BytePit.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

@Service
public class VirtualnoNatjecanjeServiceJpa implements VirtualnoNatjecanjeService {
    @Autowired
    private ZadatakService zadatakService;
    @Autowired
    private KorisnikService korisnikService;
    @Autowired
    private NatjecanjeService natjecanjeService;
    @Autowired
    private RjesenjeService rjesenjeService;
    @Autowired
    private VirtualnoNatjecanjeRepository virtualnoNatjecanjeRepo;


    @Override
    public List<VirtualnoNatjecanje> findAll() {
        return virtualnoNatjecanjeRepo.findAll();
    }

    @Override
    public VirtualnoNatjecanje getVirtualnoNatjecanje(Integer Id) {
        VirtualnoNatjecanje virtualnoNatjecanje = virtualnoNatjecanjeRepo.findByNatjecanjeId(Id);
        Assert.notNull(virtualnoNatjecanje, "Virtualno natjecanje s ID-em " + Id + " ne postoji!");
        return virtualnoNatjecanje;
    }

    @Override
    public List<VirtualnoNatjecanje> getByKorisnikId(Integer korisnikId) {
        Assert.notNull(korisnikId, "Id natjecatelja ne smije biti null");
        Optional<Korisnik> korisnik = korisnikService.fetch(korisnikId);
        Assert.isTrue(korisnik.isPresent(), "Korisnik s ID-em " + korisnikId + " ne postoji!");
        Assert.isTrue(korisnik.get().getUloga().equals(Uloga.NATJECATELJ), "Korisnik s ID-em " + korisnikId + " nije natjecatelj!");
        return virtualnoNatjecanjeRepo.findByNatjtecanteljId(korisnikId);
    }

    @Override
    public VirtualnoNatjecanje createVirtualnoNatjecanje(VirtualnoNatjecanjeDTO virtualnoNatjecanjeDTO) {
        //ako su poslani, id virtualnog natjecanja i timestamp se igonoriraju
        Korisnik korisnik = korisnikService.getKorisnik(virtualnoNatjecanjeDTO.getKorisnickoImeNatjecatelja()).get();
        Natjecanje origNatjecanje = natjecanjeService.getNatjecanje(virtualnoNatjecanjeDTO.getOrginalnoNatjecanjeId());
        Assert.notNull(origNatjecanje, "Natjecanje s ID-em " + virtualnoNatjecanjeDTO.getOrginalnoNatjecanjeId() + " ne postoji!");
        Assert.isTrue(natjecanjeService.getFinishedNatjecanja().contains(origNatjecanje), "Natjecanje s ID-em " + virtualnoNatjecanjeDTO.getOrginalnoNatjecanjeId() + " nije zavrseno!");
        VirtualnoNatjecanje virtualnoNatjecanje = new VirtualnoNatjecanje(origNatjecanje, korisnik, new Timestamp(System.currentTimeMillis()));
        virtualnoNatjecanje.setListaZadataka(List.copyOf(origNatjecanje.getZadaci()));
        virtualnoNatjecanjeRepo.save(virtualnoNatjecanje);
        return virtualnoNatjecanje;

    }

    @Override
    public List<VirtualnoNatjecanje> getByOrigNatId(Integer origNatId) {
        Assert.notNull(origNatId, "Id originalnog natjecanja ne smije biti null");
        Natjecanje orig = natjecanjeService.getNatjecanje(origNatId);
        Assert.notNull(orig, "Natjecanje s ID-em " + origNatId + " ne postoji!");
        return virtualnoNatjecanjeRepo.findByOrigNatId(origNatId);
    }

    @Override
    public Set<Zadatak> getZadaci(Integer virtualnoNatjecanjeId) {
        Assert.notNull(virtualnoNatjecanjeId, "Id virtualnog natjecanja ne smije biti null");
        VirtualnoNatjecanje virtualnoNatjecanje = virtualnoNatjecanjeRepo.findByNatjecanjeId(virtualnoNatjecanjeId);
        Assert.notNull(virtualnoNatjecanje, "Virtualno natjecanje s ID-em " + virtualnoNatjecanjeId + " ne postoji!");
        return virtualnoNatjecanje.getListaZadataka();
    }

    @Override
    public VirtualnoNatjecanje createVirtualnoNatjecanjeRandom(String korisnickoImeNatjecatelja) {

        Korisnik korisnik = korisnikService.getKorisnik(korisnickoImeNatjecatelja).get();
        List<Zadatak> allJavniZadaci = zadatakService.listAllJavniZadatak().stream().map((zad) -> zadatakService.fetch(zad.getZadatakId())).toList();
        List<Zadatak> randomZadaci = new ArrayList<>();

        Arrays.asList(TezinaZadatka.values()).forEach(tezinaZadatka -> {
        List<Zadatak> filteredZadaci = allJavniZadaci.stream().filter(zadatak -> zadatak.getTezinaZadatka().equals(tezinaZadatka)).toList();
        if(filteredZadaci.size() == 0)
            return;
        randomZadaci.add(filteredZadaci.get(new Random().nextInt(filteredZadaci.size())));
    }
    );
        VirtualnoNatjecanje virtualnoNatjecanje = new VirtualnoNatjecanje(null, korisnik, new Timestamp(System.currentTimeMillis()));
        virtualnoNatjecanje.setListaZadataka(randomZadaci);
        return virtualnoNatjecanjeRepo.save(virtualnoNatjecanje);
    }

    @Override
    public RangDTO getRang(Integer virtualnoNatjecanjeId) {
        VirtualnoNatjecanje virtualnoNatjecanje = virtualnoNatjecanjeRepo.findByNatjecanjeId(virtualnoNatjecanjeId);

        if (virtualnoNatjecanje == null || virtualnoNatjecanje.getOrginalnoNatjecanje() == null) {
            throw new IllegalArgumentException("Pogrešan ID ili nasumično generirano virtualno natjecanje!");
        }

        Integer origNatId = virtualnoNatjecanje.getOrginalnoNatjecanje().getNatjecanjeId();
        List<RangDTO> rangLista = natjecanjeService.getRangList(origNatId);
        List<Rjesenje> rjesenja = rjesenjeService.findByNatjecanjeId(virtualnoNatjecanjeId);
        Map<Integer, Double> zadatakBodovi = new HashMap<>();

        virtualnoNatjecanje.getZadaci().forEach(
                zadatak -> {
                    Optional<Rjesenje> rjesenje = rjesenja.stream().filter(r -> r.getRjesenjeId().getZadatak().getZadatakId().equals(zadatak.getZadatakId())).findFirst();
                    if (rjesenje.isPresent()){
                        zadatakBodovi.put(zadatak.getZadatakId(), rjesenje.get().getBrojTocnihPrimjera() * zadatak.getBrojBodova());
                    }
                    else{
                        zadatakBodovi.put(zadatak.getZadatakId(), 0.0);
                    }
                }
        );

        Duration vrijemeRjesavanja = Duration.between(virtualnoNatjecanje.getPocetakNatjecanja().toInstant(), rjesenja.stream().map(rjesenje -> rjesenje.getVrijemeOdgovora().toInstant()).max(Instant::compareTo).get());
        RangDTO rang = new RangDTO(virtualnoNatjecanje.getNatjecatelj().getKorisnickoIme(), zadatakBodovi, vrijemeRjesavanja);
        rangLista.add(rang);
        rangLista.sort(Comparator.comparing(RangDTO::getUkupniBodovi).thenComparing(RangDTO::getVrijemeRjesavanja).reversed());
        int index = rangLista.indexOf(rang);
        rang.setRang(index + 1);
        return rang;

    }
}
