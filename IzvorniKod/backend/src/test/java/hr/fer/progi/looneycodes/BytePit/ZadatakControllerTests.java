package hr.fer.progi.looneycodes.BytePit;

import hr.fer.progi.looneycodes.BytePit.api.controller.RegisterKorisnikDTO;
import hr.fer.progi.looneycodes.BytePit.api.controller.ZadatakController;
import hr.fer.progi.looneycodes.BytePit.api.controller.ZadatakDTO;
import hr.fer.progi.looneycodes.BytePit.api.model.Korisnik;
import hr.fer.progi.looneycodes.BytePit.api.model.TezinaZadatka;
import hr.fer.progi.looneycodes.BytePit.api.model.Uloga;
import hr.fer.progi.looneycodes.BytePit.api.model.Zadatak;
import hr.fer.progi.looneycodes.BytePit.api.repository.KorisnikRepository;
import hr.fer.progi.looneycodes.BytePit.api.repository.ZadatakRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ZadatakControllerTests {

    @Autowired
    private ZadatakController zadatakController;
    @SpyBean
    private ZadatakRepository zadatakRepository;
    @SpyBean
    private KorisnikRepository korisnikRepository;

    @Test
    public void test_returns_all_public_problems() {
        // Arrange
        List<Zadatak> problems = new ArrayList<>();

        ZadatakDTO dto1 = new ZadatakDTO(1, "voditelj1", "Zadatak1", TezinaZadatka.RECRUIT, 60, "Opis zadatka 1", false);
        ZadatakDTO dto2 = new ZadatakDTO(2, "voditelj2", "Zadatak2", TezinaZadatka.RECRUIT, 90, "Opis zadatka 2", false);

        Zadatak z1 = new Zadatak(dto1, mock(Korisnik.class));
        Zadatak z2 = new Zadatak(dto2, mock(Korisnik.class));

        problems.add(z1);
        problems.add(z2);

        when(zadatakRepository.findAllJavniZadatak()).thenReturn(problems);

        // Act
        List<ZadatakDTO> result = zadatakController.listAll();
        // Assert
        assert(result.size() == 2);
    }

    @Test
    public void test_admin_can_update_any_problem() {
        // Arrange
        Authentication auth = new UsernamePasswordAuthenticationToken("admin", "adminSifa", Collections.singleton(new SimpleGrantedAuthority("VODITELJ")));
        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(auth);
        UserDetails userDetails = new User("admin", "adminSifa", Collections.singleton(new SimpleGrantedAuthority("ADMIN")));

        RegisterKorisnikDTO dto = new RegisterKorisnikDTO();
        dto.setKorisnickoIme("DeanKotiga");
        dto.setIme("Dean");
        dto.setPrezime("Kotiga");
        dto.setLozinka("MladenVukorepaJeKul");
        dto.setEmail("svenmarcelic@gmail.com");
        dto.setRequestedUloga(Uloga.NATJECATELJ);
        Korisnik korisnik = new Korisnik(dto);
        korisnik.setUloga(Uloga.VODITELJ);

        ZadatakDTO zadatakDTO = new ZadatakDTO();
        zadatakDTO.setNazivZadatka("Zadatak");
        zadatakDTO.setVoditelj("DeanKotiga");
        zadatakDTO.setTezinaZadatka(TezinaZadatka.REALISM);
        zadatakDTO.setVremenskoOgranicenje(50);
        zadatakDTO.setTekstZadatka("tekst");
        zadatakDTO.setPrivatniZadatak(false);
        Zadatak zadatak = new Zadatak(zadatakDTO, korisnik);

        korisnikRepository.save(korisnik);
        Integer id = zadatakRepository.save(zadatak).getZadatakId();

        // Act
        zadatak.setNazivZadatka("Novi naziv");
        zadatak.setTezinaZadatka(TezinaZadatka.VETERAN);
        Zadatak updatedZadatak = zadatakController.updateKorisnik(id, zadatak, userDetails);

        // Assert
        assertNotNull(updatedZadatak);
        assertEquals(zadatak.getNazivZadatka(), updatedZadatak.getNazivZadatka());
        assertEquals(zadatak.getTezinaZadatka(), updatedZadatak.getTezinaZadatka());

    }

}
