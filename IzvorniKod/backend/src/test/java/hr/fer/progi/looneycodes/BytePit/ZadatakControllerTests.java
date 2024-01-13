package hr.fer.progi.looneycodes.BytePit;

import hr.fer.progi.looneycodes.BytePit.api.controller.ZadatakController;
import hr.fer.progi.looneycodes.BytePit.api.controller.ZadatakDTO;
import hr.fer.progi.looneycodes.BytePit.api.model.Korisnik;
import hr.fer.progi.looneycodes.BytePit.api.model.TezinaZadatka;
import hr.fer.progi.looneycodes.BytePit.api.model.Zadatak;
import hr.fer.progi.looneycodes.BytePit.api.repository.ZadatakRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ZadatakControllerTests {

    @Autowired
    private ZadatakController zadatakController;
    @MockBean
    private ZadatakRepository zadatakRepository;

    @Test
    public void test_returns_all_public_problems() {
        // Arrange
        List<Zadatak> problems = new ArrayList<>();
        when(zadatakRepository.findAllJavniZadatak()).thenReturn(problems);
        // Act
        List<ZadatakDTO> result = zadatakController.listAll();
        // Assert
        assertTrue(result.isEmpty());
    }
    @Test
    public void test_admin_can_update_any_problem() {
        // Arrange
        Authentication auth = new UsernamePasswordAuthenticationToken(
                "admin", "adminSifra",
                Collections.singleton(new SimpleGrantedAuthority("ADMIN")));
        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(auth);
        UserDetails userDetails = new User("admin", "adminSifra",
                Collections.singleton(new SimpleGrantedAuthority("ADMIN")));

        Zadatak zadatak = new Zadatak();
        zadatak.setNazivZadatka("Naziv");
        zadatak.setTezinaZadatka(TezinaZadatka.RECRUIT);
        zadatak.setVoditelj(mock(Korisnik.class));
        when(zadatakRepository.save(any(Zadatak.class))).thenAnswer(i -> (Zadatak) i.getArguments()[0]);
        when(zadatakRepository.findById(any(Integer.class))).thenReturn(Optional.of(zadatak));

        Zadatak updates = new Zadatak();
        updates.setNazivZadatka("Novi naziv");
        updates.setTezinaZadatka(TezinaZadatka.VETERAN);

        // Act
        Zadatak updatedZadatak = zadatakController.updateKorisnik(1, updates, userDetails);

        // Assert
        assertNotNull(updatedZadatak);
        assertEquals(updates.getNazivZadatka(), updatedZadatak.getNazivZadatka());
        assertEquals(updates.getTezinaZadatka(), updatedZadatak.getTezinaZadatka());

    }

}
