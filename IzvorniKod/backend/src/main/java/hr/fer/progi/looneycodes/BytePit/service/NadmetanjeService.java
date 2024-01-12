package hr.fer.progi.looneycodes.BytePit.service;

import org.springframework.stereotype.Service;

import hr.fer.progi.looneycodes.BytePit.api.controller.NadmetanjeInfoDTO;

/**
 * NadmetanjeService
 */
@Service
public interface NadmetanjeService {
  /**
   * vraca boolean virtualno? + set svih imena/id-eva zadataka
   */
  public NadmetanjeInfoDTO getInfo(int id);  
}
