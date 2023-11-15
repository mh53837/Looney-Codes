package hr.fer.progi.looneycodes.BytePit.service.impl;

import hr.fer.progi.looneycodes.BytePit.api.controller.VirtualnoNatjecanjeDTO;
import hr.fer.progi.looneycodes.BytePit.api.model.Korisnik;
import hr.fer.progi.looneycodes.BytePit.api.model.Natjecanje;
import hr.fer.progi.looneycodes.BytePit.api.model.Uloga;
import hr.fer.progi.looneycodes.BytePit.api.model.VirtualnoNatjecanje;
import hr.fer.progi.looneycodes.BytePit.api.repository.VirutalnoNatjecanjeRepository;
import hr.fer.progi.looneycodes.BytePit.service.KorisnikService;
import hr.fer.progi.looneycodes.BytePit.service.NatjecanjeService;
import hr.fer.progi.looneycodes.BytePit.service.VirtualnoNatjecanjeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class VirtualnoNatjecanjeServiceJpa implements VirtualnoNatjecanjeService {
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
        Assert.isTrue(korisnik.get().getUloga().equals(Uloga.NATJECATELJ), "Korisnik s ID-em " + virtualnoNatjecanjeDTO.getNatjecateljId() + " nije natjecatelj!");
        Natjecanje orig = null; //pretpostavka da originalno natjecanje moze biti null

        if (virtualnoNatjecanjeDTO.getOrginalnoNatjecanjeId() != null) {
            orig = natjecanjeService.getNatjecanje(virtualnoNatjecanjeDTO.getOrginalnoNatjecanjeId());
            Assert.notNull(orig, "Natjecanje s ID-em " + virtualnoNatjecanjeDTO.getOrginalnoNatjecanjeId() + " ne postoji!");
        }
        return virtualnoNatjecanjeRepo.save(new VirtualnoNatjecanje(orig, korisnik.get(), new Timestamp(System.currentTimeMillis())));

    }

    @Override
    public List<VirtualnoNatjecanje> getByOrigNatId(Integer origNatId) {
        Assert.notNull(origNatId, "Id originalnog natjecanja ne smije biti null");
        Natjecanje orig = natjecanjeService.getNatjecanje(origNatId);
        Assert.notNull(orig, "Natjecanje s ID-em " + origNatId + " ne postoji!");
        return virtualnoNatjecanjeRepo.findByOrigNatId(origNatId);
    }
}
