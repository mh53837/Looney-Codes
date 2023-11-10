package hr.fer.progi.looneycodes.BytePit.service;

import hr.fer.progi.looneycodes.BytePit.api.controller.CreateNatjecanjeDTO;
import hr.fer.progi.looneycodes.BytePit.api.model.Natjecanje;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public interface NatjecanjeService {
    public Natjecanje createNatjecanje(String nazivNatjecanja,
                                       Timestamp pocetakNatjecanja, Timestamp krajNatjecanja,
                                       Integer voditeljId);
    public Natjecanje updateNatjecanje(CreateNatjecanjeDTO natjecanjeDTO);
    public Natjecanje getNatjecanje(Integer natjecanjeId);
    public List<Natjecanje> getAllNatjecanja();
    public List<Natjecanje> getNatjecanjaByKorisnikId(Integer korisnikId);
   // public Natjecanje updateNatjecanje(Natjecanje natjecanje);
    public void deleteNatjecanje(Integer natjecanjeId);


}
