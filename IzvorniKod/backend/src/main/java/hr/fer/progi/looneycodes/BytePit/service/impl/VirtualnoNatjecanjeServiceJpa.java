package hr.fer.progi.looneycodes.BytePit.service.impl;

import hr.fer.progi.looneycodes.BytePit.api.controller.VirtualnoNatjecanjeDTO;
import hr.fer.progi.looneycodes.BytePit.api.model.*;
import hr.fer.progi.looneycodes.BytePit.api.repository.VirutalnoNatjecanjeRepository;
import hr.fer.progi.looneycodes.BytePit.service.KorisnikService;
import hr.fer.progi.looneycodes.BytePit.service.NatjecanjeService;
import hr.fer.progi.looneycodes.BytePit.service.VirtualnoNatjecanjeService;
import hr.fer.progi.looneycodes.BytePit.service.ZadatakService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.sql.Timestamp;
import java.util.*;

@Service
public class VirtualnoNatjecanjeServiceJpa implements VirtualnoNatjecanjeService {
    @Autowired
    private ZadatakService zadatakService;
    @Autowired
    private KorisnikService korisnikService;
    @Autowired
    NatjecanjeService natjecanjeService;
    @Autowired
    private VirutalnoNatjecanjeRepository virtualnoNatjecanjeRepo;


    @Override
    public List<VirtualnoNatjecanje> findAll() {
        return virtualnoNatjecanjeRepo.findAll();
    }

    @Override
    public VirtualnoNatjecanje getVirtualnoNatjecanje(Integer Id) {
        VirtualnoNatjecanje virtualnoNatjecanje = virtualnoNatjecanjeRepo.findByVirtualnoNatjecanjeId(Id);
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
        Assert.notNull(virtualnoNatjecanjeDTO.getNatjecateljId(), "Id natjecatelja ne smije biti null!");
        Optional<Korisnik> korisnik = korisnikService.fetch(virtualnoNatjecanjeDTO.getNatjecateljId());
        Assert.isTrue(korisnik.isPresent(), "Korisnik s ID-em " + virtualnoNatjecanjeDTO.getNatjecateljId() + " ne postoji!");
        Natjecanje origNatjecanje = natjecanjeService.getNatjecanje(virtualnoNatjecanjeDTO.getOrginalnoNatjecanjeId());
        Assert.isTrue(natjecanjeService.getFinishedNatjecanja().contains(origNatjecanje), "Natjecanje s ID-em " + virtualnoNatjecanjeDTO.getOrginalnoNatjecanjeId() + " nije zavrseno!");
        VirtualnoNatjecanje virtualnoNatjecanje = new VirtualnoNatjecanje(origNatjecanje, korisnik.get(), new Timestamp(System.currentTimeMillis()));
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
    public List<Zadatak> getZadaci(Integer virtualnoNatjecanjeId) {
        Assert.notNull(virtualnoNatjecanjeId, "Id virtualnog natjecanja ne smije biti null");
        VirtualnoNatjecanje virtualnoNatjecanje = virtualnoNatjecanjeRepo.findByVirtualnoNatjecanjeId(virtualnoNatjecanjeId);
        Assert.notNull(virtualnoNatjecanje, "Virtualno natjecanje s ID-em " + virtualnoNatjecanjeId + " ne postoji!");
        return virtualnoNatjecanje.getListaZadataka();
    }

    @Override
    public VirtualnoNatjecanje createVirtualnoNatjecanjeRandom(VirtualnoNatjecanjeDTO virtualnoNatjecanjeDTO) {
        Assert.notNull(virtualnoNatjecanjeDTO.getNatjecateljId(), "Id natjecatelja ne smije biti null!");
        Optional<Korisnik> korisnik = korisnikService.fetch(virtualnoNatjecanjeDTO.getNatjecateljId());
        Assert.isTrue(korisnik.isPresent(), "Korisnik s ID-em " + virtualnoNatjecanjeDTO.getNatjecateljId() + " ne postoji!");

        List<Zadatak> allJavniZadaci = zadatakService.listAllJavniZadatak().stream().map((zad) -> zadatakService.fetch(zad.getZadatakId())).toList();
        List<Zadatak> randomZadaci = new ArrayList<>();

        Arrays.asList(TezinaZadatka.values()).forEach(tezinaZadatka -> {
        List<Zadatak> filteredZadaci = allJavniZadaci.stream().filter(zadatak -> zadatak.getTezinaZadatka().equals(tezinaZadatka)).toList();
        if(filteredZadaci.size() == 0)
            return;
        randomZadaci.add(filteredZadaci.get(new Random().nextInt(filteredZadaci.size())));
    }
    );
        VirtualnoNatjecanje virtualnoNatjecanje = new VirtualnoNatjecanje(null, korisnik.get(), new Timestamp(System.currentTimeMillis()));
        virtualnoNatjecanje.setListaZadataka(randomZadaci);
        return virtualnoNatjecanjeRepo.save(virtualnoNatjecanje);
    }
}
