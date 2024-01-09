package hr.fer.progi.looneycodes.BytePit.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.fer.progi.looneycodes.BytePit.api.controller.NadmetanjeInfoDTO;
import hr.fer.progi.looneycodes.BytePit.api.model.Nadmetanje;
import hr.fer.progi.looneycodes.BytePit.api.model.VirtualnoNatjecanje;
import hr.fer.progi.looneycodes.BytePit.api.repository.NadmetanjeRepository;
import hr.fer.progi.looneycodes.BytePit.service.NadmetanjeService;
/**
 * NadmetanjeServiceJpa
 */
@Service
public class NadmetanjeServiceJpa implements NadmetanjeService {
  @Autowired
  NadmetanjeRepository nadmetanjeRepository;

  @Override
  public NadmetanjeInfoDTO getInfo(int id) {
    Nadmetanje nadmetanje;
    try {
      nadmetanje = nadmetanjeRepository.findByNatjecanjeId(id);
    }
    catch (Exception e) {
      throw new IllegalArgumentException(e.getMessage());
    }

    NadmetanjeInfoDTO out = new NadmetanjeInfoDTO();
    out.setVirtualno(nadmetanje instanceof VirtualnoNatjecanje);

    Set<NadmetanjeInfoDTO.ZadatakInfo> zadaci = new HashSet<>();
    nadmetanje.getZadaci().forEach(zadatak -> zadaci.add(new NadmetanjeInfoDTO.ZadatakInfo(zadatak)));
    out.setZadaci(zadaci);

    return out;
  }
}
