package hr.fer.progi.looneycodes.BytePit.service;

import hr.fer.progi.looneycodes.BytePit.api.controller.VirtualnoNatjecanjeDTO;
import hr.fer.progi.looneycodes.BytePit.api.model.VirtualnoNatjecanje;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface VirtualnoNatjecanjeService {
    public List<VirtualnoNatjecanje> findAll();

    public VirtualnoNatjecanje getVirtualnoNatjecanje(Integer Id);

    public List<VirtualnoNatjecanje> getByKorisnikId(Integer korisnikId);

    public VirtualnoNatjecanje createVirtualnoNatjecanje(VirtualnoNatjecanjeDTO virtualnoNatjecanjeDTO);

    public List<VirtualnoNatjecanje> getByOrigNatId(Integer origNatId);

}
