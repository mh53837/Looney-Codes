package hr.fer.progi.looneycodes.BytePit.api.controller;


import hr.fer.progi.looneycodes.BytePit.api.model.Korisnik;
import hr.fer.progi.looneycodes.BytePit.api.model.VirtualnoNatjecanje;
import hr.fer.progi.looneycodes.BytePit.service.KorisnikService;
import hr.fer.progi.looneycodes.BytePit.service.VirtualnoNatjecanjeService;
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
import java.util.Set;
import java.util.stream.Collectors;


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
        if (Objects.isNull(user))
            throw new AccessDeniedException("You must be logged in for that!");

        Optional<Korisnik> natjecatelj = korisnikService.getKorisnik(virtualnoNatjecanjeDTO.getKorisnickoImeNatjecatelja());
        if ((natjecatelj.isEmpty() || !virtualnoNatjecanjeDTO.getKorisnickoImeNatjecatelja().equals(user.getUsername()))
                && !user.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN")))
            throw new IllegalStateException("Morate biti taj natjecatelj ili admin!");


        virtualnoNatjecanjeDTO.setKorisnickoImeNatjecatelja(natjecatelj.get().getKorisnickoIme());
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
            return new VirtualnoNatjecanjeDTO(virtualnoNatjecanje);
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
        return new VirtualnoNatjecanjeDTO(virtualnoNatjecanjeService.getVirtualnoNatjecanje(Id));
    }

    /**
     * Vraća virtualna natjecanja povezana s zadanim natjecateljem
     *
     * @param korisnickoImeNatjecatelja korisničko ime natjecatelja
     * @return lista virtualnih natjecanja
     */
    @GetMapping("/get/natjecatelj/{korisnickoImeNatjecatelja}")
    public List<VirtualnoNatjecanjeDTO> getVirtualnoNatjecanjeByKorisnikI(@PathVariable String korisnickoImeNatjecatelja, @AuthenticationPrincipal UserDetails user) {
        if (Objects.isNull(user))
            throw new AccessDeniedException("You must be logged in for that!");

        Optional<Korisnik> natjecatelj = korisnikService.getKorisnik(korisnickoImeNatjecatelja);
        if ((natjecatelj.isEmpty() || !korisnickoImeNatjecatelja.equals(user.getUsername()))
                && !user.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN")))
            throw new IllegalStateException("Morate biti taj natjecatelj ili admin!");

        return virtualnoNatjecanjeService.getByKorisnikId(natjecatelj.get().getKorisnikId()).stream().map(virtualnoNatjecanje -> {
            return new VirtualnoNatjecanjeDTO(virtualnoNatjecanje);
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
            return new VirtualnoNatjecanjeDTO(virtualnoNatjecanje);
        }).toList();
    }

    /**
     * Vraća sve zadatke povezane s virtualnim natjecanjem
     *
     * @param virtualnoNatjecanjeId identifikator virtualnog natjecanja
     * @return lista zadataka
     */
    @GetMapping("/get/zadaci/{virtualnoNatjecanjeId}")
    public Set<ZadatakDTO> getZadaci(@PathVariable Integer virtualnoNatjecanjeId) {
        return virtualnoNatjecanjeService.getZadaci(virtualnoNatjecanjeId).stream().map(zadatak -> {
            return new ZadatakDTO(zadatak);
        }).collect(Collectors.toSet());
    }

    /**
     * Stvara virtualno natjecanje s nasumičnim zadacima
     *
     * @param korisnickoImeNatjecatelja korisničko ime natjecatelja
     */

    @PostMapping("/new/random/{korisnickoImeNatjecatelja}")
    public VirtualnoNatjecanjeDTO createVirtualnoNatjecanjeRandom(@PathVariable String korisnickoImeNatjecatelja, @AuthenticationPrincipal UserDetails user) {
        if (Objects.isNull(user))
            throw new AccessDeniedException("You must be logged in for that!");

        Optional<Korisnik> natjecatelj = korisnikService.getKorisnik(korisnickoImeNatjecatelja);
        if ((natjecatelj.isEmpty() || !korisnickoImeNatjecatelja.equals(user.getUsername()))
                && !user.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN")))
            throw new IllegalStateException("Morate biti taj natjecatelj ili admin!");

        return new VirtualnoNatjecanjeDTO(virtualnoNatjecanjeService.createVirtualnoNatjecanjeRandom(korisnickoImeNatjecatelja));
    }

    /**
     * Vraća rang naspram orginalnog natjecanja
     *
     * @param virtualnoNatjecanjeId
     * @return rang
     */
    @GetMapping("/get/rang/{virtualnoNatjecanjeId}")
    public List<RangDTO> getRang(@PathVariable Integer virtualnoNatjecanjeId) {
        return virtualnoNatjecanjeService.getRang(virtualnoNatjecanjeId);
    }
}
