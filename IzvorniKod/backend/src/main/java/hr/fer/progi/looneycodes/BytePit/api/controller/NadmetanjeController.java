package hr.fer.progi.looneycodes.BytePit.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hr.fer.progi.looneycodes.BytePit.service.NadmetanjeService;

/**
 * NadmetanjeController
 */
@RestController
@RequestMapping("/api/nadmetanja")
public class NadmetanjeController {
  @Autowired
  NadmetanjeService nadmetanjeService;

  @GetMapping("/info/{id}")
  public NadmetanjeInfoDTO getInfo(@PathVariable Integer id) {
    return nadmetanjeService.getInfo(id);
  }
}
