package hr.fer.progi.looneycodes.BytePit.api.controller;


import hr.fer.progi.looneycodes.BytePit.api.model.VirtualnoNatjecanje;
import hr.fer.progi.looneycodes.BytePit.api.model.Zadatak;
import hr.fer.progi.looneycodes.BytePit.service.KorisnikService;
import hr.fer.progi.looneycodes.BytePit.service.VirtualnoNatjecanjeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;


/**
 * Kontroler koji se koristi za pristup metodama vezanim uz VirtualnoNatjecanje entitet
 *
 * @see VirtualnoNatjecanjeService
 * @see VirtualnoNatjecanje
 */
@RestController
@RequestMapping("/api/virtualnaNatjecanja")
public class VirtualnoNatjecanjeController {

    @Autowired
    private VirtualnoNatjecanjeService virtualnoNatjecanjeService;

    @Autowired
    private KorisnikService korisnikService;

    /**
     * Stvara virtualno natjecanje i sprema ga u bazu
     *
     * @param virtualnoNatjecanjeDTO objekt s podacima o virtualnom natjecanju
     * @return virtualno natjecanje
     */
    @PostMapping("/new")
    @Secured("NATJECATELJ")
    public VirtualnoNatjecanjeDTO createVirtualnoNatjecanje(@RequestBody VirtualnoNatjecanjeDTO virtualnoNatjecanjeDTO, @AuthenticationPrincipal UserDetails user) {
        if(Objects.isNull(user))
          throw new AccessDeniedException("You must be logged in for that!");

        int natjecateljId = korisnikService.getKorisnik(user.getUsername()).get().getKorisnikId();
        virtualnoNatjecanjeDTO.setNatjecateljId(natjecateljId);

        return new VirtualnoNatjecanjeDTO(virtualnoNatjecanjeService.createVirtualnoNatjecanje(virtualnoNatjecanjeDTO));
    }

    /**
     * Vraća sva virtualna natjecanja
     *
     * @return lista virtualnih natjecanja
     */
    @GetMapping("/all")
    @Secured("ADMIN")
    public List<VirtualnoNatjecanjeDTO> listAll() {
        return virtualnoNatjecanjeService.findAll().stream().map(virtualnoNatjecanje -> {
            return new VirtualnoNatjecanjeDTO(virtualnoNatjecanje.getVirtualnoNatjecanjeId(), virtualnoNatjecanje.getOrginalnoNatjecanje().getNatjecanjeId(), virtualnoNatjecanje.getNatjecatelj().getKorisnikId(), virtualnoNatjecanje.getVrijemePocetka());
        }).toList();
    }

    /**
     * Vraća virtualno natjecanje prema jedinstvenom identifikatoru
     *
     * @param Id identifikator virtualnog natjecanja
     * @return virtualno natjecanje
     */
    @GetMapping("/get/{Id}")
    @Secured("ADMIN")
    public VirtualnoNatjecanjeDTO getVirtualnoNatjecanje(@PathVariable Integer Id) {
        VirtualnoNatjecanje virtualnoNatjecanje = virtualnoNatjecanjeService.getVirtualnoNatjecanje(Id);
        return new VirtualnoNatjecanjeDTO(virtualnoNatjecanje.getVirtualnoNatjecanjeId(), virtualnoNatjecanje.getOrginalnoNatjecanje().getNatjecanjeId(), virtualnoNatjecanje.getNatjecatelj().getKorisnikId(), virtualnoNatjecanje.getVrijemePocetka());
    }

    /**
     * Vraća virtualna natjecanja povezana s zadanim natjecateljem
     *
     * @param natjecateljId identifikator natjecatelja
     * @return lista virtualnih natjecanja
     */
    @GetMapping("/get/natjecatelj/{natjecateljId}")
    public List<VirtualnoNatjecanjeDTO> getVirtualnoNatjecanjeByKorisnikId(@PathVariable Integer natjecateljId, @AuthenticationPrincipal UserDetails user) {
        if(Objects.isNull(user))
          throw new AccessDeniedException("You must be logged in for that!");

        int korisnikId = korisnikService.getKorisnik(user.getUsername()).get().getKorisnikId();
        if(!user.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))
            && korisnikId != natjecateljId)
          throw new AccessDeniedException("Niste taj natjecatelj ni admin!");

        return virtualnoNatjecanjeService.getByKorisnikId(natjecateljId).stream().map(virtualnoNatjecanje -> {
            return new VirtualnoNatjecanjeDTO(virtualnoNatjecanje.getVirtualnoNatjecanjeId(), virtualnoNatjecanje.getOrginalnoNatjecanje().getNatjecanjeId(), virtualnoNatjecanje.getNatjecatelj().getKorisnikId(), virtualnoNatjecanje.getVrijemePocetka());
        }).toList();
    }

    /**
     * Vraća virtualna natjecanja povezana s zadanim originalnim natjecanjem
     *
     * @param origNatId identifikator originalnog natjecanja
     * @return lista virtualnih natjecanja
     */
    @GetMapping("/get/origNatjecanje/{origNatId}")
    @Secured("ADMIN")
    public List<VirtualnoNatjecanjeDTO> getVirtualnoNatjecanjeByOrigNatId(@PathVariable Integer origNatId) {
        return virtualnoNatjecanjeService.getByOrigNatId(origNatId).stream().map(virtualnoNatjecanje -> {
            return new VirtualnoNatjecanjeDTO(virtualnoNatjecanje.getVirtualnoNatjecanjeId(), virtualnoNatjecanje.getOrginalnoNatjecanje().getNatjecanjeId(), virtualnoNatjecanje.getNatjecatelj().getKorisnikId(), virtualnoNatjecanje.getVrijemePocetka());
        }).toList();
    }

    /**
     * Vraća sve zadatke povezane s virtualnim natjecanjem
     * @param virtualnoNatjecanjeId identifikator virtualnog natjecanja
     * @return lista zadataka
     *
     */
    @GetMapping("/get/zadaci/{virtualnoNatjecanjeId}")
    public List<Zadatak> getZadaci(@PathVariable Integer virtualnoNatjecanjeId) {
        return virtualnoNatjecanjeService.getZadaci(virtualnoNatjecanjeId);
    }

    /**
     * Stvara virtualno natjecanje s nasumičnim zadacima
     * @param natjecateljID identifikator natjecatelja
     *
     */

    @PostMapping("/new/random/{natjecateljID}")
    public VirtualnoNatjecanjeDTO createVirtualnoNatjecanjeRandom(@PathVariable Integer natjecateljID) {
        VirtualnoNatjecanjeDTO virtualnoNatjecanjeDTO = new VirtualnoNatjecanjeDTO();
        virtualnoNatjecanjeDTO.setNatjecateljId(natjecateljID);
        VirtualnoNatjecanje virtualnoNatjecanje = virtualnoNatjecanjeService.createVirtualnoNatjecanjeRandom(virtualnoNatjecanjeDTO);
        return new VirtualnoNatjecanjeDTO(virtualnoNatjecanje.getVirtualnoNatjecanjeId(), null, virtualnoNatjecanje.getNatjecatelj().getKorisnikId(), virtualnoNatjecanje.getVrijemePocetka());
    }

}
