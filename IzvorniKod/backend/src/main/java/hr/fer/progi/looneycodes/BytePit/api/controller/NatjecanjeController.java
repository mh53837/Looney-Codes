package hr.fer.progi.looneycodes.BytePit.api.controller;

import hr.fer.progi.looneycodes.BytePit.api.model.Korisnik;
import hr.fer.progi.looneycodes.BytePit.api.model.Natjecanje;
import hr.fer.progi.looneycodes.BytePit.api.model.Zadatak;
import hr.fer.progi.looneycodes.BytePit.service.KorisnikService;
import hr.fer.progi.looneycodes.BytePit.service.NatjecanjeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Kontroler koji se koristi za pristup metodama vezanim uz Natjecanje entitet
 *
 * @see NatjecanjeService
 * @see Natjecanje
 */
@RestController
@RequestMapping("/api/natjecanja")
public class NatjecanjeController {

    @Autowired
    private NatjecanjeService natjecanjeService;

    @Autowired
    private KorisnikService korisnikService;

    /**
     * Stvara natjecanje i sprema ga u bazu
     *
     * @param natjecanjeDTO objekt s podacima o natjecanju
     * @return natjecanje
     */
    @PostMapping("/new")
    @Secured({"VODITELJ", "ADMIN"})
    public Natjecanje createNatjecanje(@RequestBody CreateNatjecanjeDTO natjecanjeDTO, @AuthenticationPrincipal UserDetails user) {
        if(Objects.isNull(user))
          throw new AccessDeniedException("You must be logged in for that!");

        Optional<Korisnik> voditelj = korisnikService.getKorisnik(user.getUsername());

        if((voditelj.isEmpty() || !natjecanjeDTO.getVoditeljId().equals(voditelj.get().getKorisnikId()))
            && !user.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN")))
          throw new IllegalStateException("Morate biti voditelj tog natjecanja ili admin!");
    
        return natjecanjeService.createNatjecanje(natjecanjeDTO);
    }

    /**
     * Vraća natjecanje prema jedinstvenom identifikatoru
     *
     * @param natjecanjeId identifikator natjecanja
     * @return natjecanje
     */
    @GetMapping("/get/{natjecanjeId}")
    public CreateNatjecanjeDTO getNatjecanje(@PathVariable Integer natjecanjeId) {
        Natjecanje natjecanje = natjecanjeService.getNatjecanje(natjecanjeId);
        return new CreateNatjecanjeDTO(natjecanje.getNatjecanjeId(), natjecanje.getNazivNatjecanja(), natjecanje.getPocetakNatjecanja(), natjecanje.getKrajNatjecanja(), natjecanje.getVoditelj().getKorisnikId());
    }

    /**
     * Vraća sva natjecanja
     *
     * @return lista natjecanja
     */
    @GetMapping("/all")
    public List<CreateNatjecanjeDTO> listAll() {
        return natjecanjeService.listAllNatjecanja().stream().map(natjecanje -> {
            return new CreateNatjecanjeDTO(natjecanje.getNatjecanjeId(), natjecanje.getNazivNatjecanja(), natjecanje.getPocetakNatjecanja(), natjecanje.getKrajNatjecanja(), natjecanje.getVoditelj().getKorisnikId());
        }).toList();
    }

    /**
     * Vraća natjecanja povezana s zadanim voditeljem
     *
     * @param voditeljId
     * @return lista natjecanja
     */
    @GetMapping("/get/voditelj/{voditeljId}")
    public List<CreateNatjecanjeDTO> getNatjecanjaByKorisnikId(@PathVariable Integer voditeljId) {
        return natjecanjeService.getNatjecanjaByKorisnikId(voditeljId).stream().map(natjecanje -> {
            return new CreateNatjecanjeDTO(natjecanje.getNatjecanjeId(), natjecanje.getNazivNatjecanja(), natjecanje.getPocetakNatjecanja(), natjecanje.getKrajNatjecanja(), natjecanje.getVoditelj().getKorisnikId());
        }).toList();
    }

    /**
     * Metoda koja ažurira postojeće natjecanje
     *
     * @param natjecanjeDTO DTO objekt s ažuriranim podacima o natjecanju
     * @return referenca na azurirano natjecanje
     */
    @PostMapping("/update")
    @Secured({"VODITELJ", "ADMIN"})
    public Natjecanje updateNatjecanje(@RequestBody CreateNatjecanjeDTO natjecanjeDTO, @AuthenticationPrincipal UserDetails user) {
        if(Objects.isNull(user))
          throw new AccessDeniedException("You must be logged in for that!");

        Optional<Korisnik> voditelj = korisnikService.getKorisnik(user.getUsername());
        if((voditelj.isEmpty() || !natjecanjeDTO.getVoditeljId().equals(voditelj.get().getKorisnikId()))
            && !user.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN")))
          throw new IllegalStateException("Morate biti voditelj tog natjecanja ili admin!");

        return natjecanjeService.updateNatjecanje(natjecanjeDTO);
    }

    /**
     * Dohvaća sva natjecanja koja se tek trebaju održati
     *
     * @return lista natjecanja
     */

    @GetMapping("/get/upcoming")
    public List<CreateNatjecanjeDTO> getUpcomingNatjecanja() {
        return natjecanjeService.getUpcomingNatjecanja().stream().map(natjecanje -> {
            return new CreateNatjecanjeDTO(natjecanje.getNatjecanjeId(), natjecanje.getNazivNatjecanja(), natjecanje.getPocetakNatjecanja(), natjecanje.getKrajNatjecanja(), natjecanje.getVoditelj().getKorisnikId());
        }).toList();
    }

    /**
     * Dohvaća sva natjecanja koja su u tijeku
     *
     * @return lista natjecanja
     */

    @GetMapping("/get/ongoing")
    public List<CreateNatjecanjeDTO> getOngoingNatjecanja() {
        return natjecanjeService.getOngoingNatjecanja().stream().map(natjecanje -> {
            return new CreateNatjecanjeDTO(natjecanje.getNatjecanjeId(), natjecanje.getNazivNatjecanja(), natjecanje.getPocetakNatjecanja(), natjecanje.getKrajNatjecanja(), natjecanje.getVoditelj().getKorisnikId());
        }).toList();
    }

    /**
     * Dohvaća sva natjecanja koja su završena
     *
     * @return lista natjecanja
     */

    @GetMapping("/get/finished")
    public List<CreateNatjecanjeDTO> getFinishedNatjecanja() {
        return natjecanjeService.getFinishedNatjecanja().stream().map(natjecanje -> {
            return new CreateNatjecanjeDTO(natjecanje.getNatjecanjeId(), natjecanje.getNazivNatjecanja(), natjecanje.getPocetakNatjecanja(), natjecanje.getKrajNatjecanja(), natjecanje.getVoditelj().getKorisnikId());
        }).toList();
    }

    /**
     * Dohvaća sve zadatke povezane s zadanim natjecanjem
     * @param natjecanjeId
     * @return lista zadataka
     */
    @GetMapping("/get/zadaci/{natjecanjeId}")
    public List<Zadatak> getZadaciByNatjecanjeId(@PathVariable Integer natjecanjeId) {
        return natjecanjeService.getZadaciByNatjecanjeId(natjecanjeId);
    }

    /**
     * Metoda koja dodaje zadatak u natjecanje
     * @param natjecanjeId
     * @param zadatakId
     */

    @PostMapping("/add/zadatak/{natjecanjeId}/{zadatakId}")
    @Secured({"VODITELJ", "ADMIN"})
    public void addZadatakToNatjecanje(@PathVariable Integer natjecanjeId, @PathVariable Integer zadatakId, @AuthenticationPrincipal UserDetails user) {
        if(Objects.isNull(user))
          throw new AccessDeniedException("You must be logged in for that!");

        Optional<Korisnik> voditelj = korisnikService.getKorisnik(user.getUsername());
        if((voditelj.isEmpty() || !natjecanjeService.getNatjecanje(natjecanjeId).getVoditelj().getKorisnikId().equals(voditelj.get().getKorisnikId()))
            && !user.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN")))
          throw new IllegalStateException("Morate biti voditelj tog natjecanja ili admin!");


        natjecanjeService.addZadatakToNatjecanje(natjecanjeId, zadatakId);
    }

    /**
     * Metoda koja uklanja zadatak iz natjecanja
     * @param natjecanjeId
     * @param zadatakId
     */

    @PostMapping("/remove/zadatak/{natjecanjeId}/{zadatakId}")
    @Secured({"VODITELJ", "ADMIN"})
    public void removeZadatakFromNatjecanje(@PathVariable Integer natjecanjeId, @PathVariable Integer zadatakId, @AuthenticationPrincipal UserDetails user) {
        if(Objects.isNull(user))
          throw new AccessDeniedException("You must be logged in for that!");

        Optional<Korisnik> voditelj = korisnikService.getKorisnik(user.getUsername());
        if((voditelj.isEmpty() || !natjecanjeService.getNatjecanje(natjecanjeId).getVoditelj().getKorisnikId().equals(voditelj.get().getKorisnikId()))
            && !user.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN")))
          throw new IllegalStateException("Morate biti voditelj tog natjecanja ili admin!");

        natjecanjeService.removeZadatakFromNatjecanje(natjecanjeId, zadatakId);
    }


}

