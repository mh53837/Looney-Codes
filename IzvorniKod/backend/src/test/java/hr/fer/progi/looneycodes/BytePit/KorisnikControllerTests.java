package hr.fer.progi.looneycodes.BytePit;

import hr.fer.progi.looneycodes.BytePit.api.controller.RegisterKorisnikDTO;
import hr.fer.progi.looneycodes.BytePit.api.controller.KorisnikController;
import hr.fer.progi.looneycodes.BytePit.api.model.Korisnik;
import hr.fer.progi.looneycodes.BytePit.api.model.Uloga;
import hr.fer.progi.looneycodes.BytePit.api.repository.KorisnikRepository;
import hr.fer.progi.looneycodes.BytePit.service.RequestDeniedException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class KorisnikControllerTests {

    @Autowired
    private KorisnikController korisnikController;
    @MockBean
    private KorisnikRepository korisnikRepository;

    @Test
    public void test_register_new_user_with_valid_data_and_image() {
        // Arrange
        MultipartFile file = new MockMultipartFile("image", "test.jpg", "image/jpeg", "test".getBytes());
        RegisterKorisnikDTO dto = new RegisterKorisnikDTO();
        dto.setKorisnickoIme("DeanKotiga");
        dto.setIme("Dean");
        dto.setPrezime("Kotiga");
        dto.setLozinka("MladenVukorepaJeKul");
        dto.setEmail("dean@kotiga.com");
        dto.setRequestedUloga(Uloga.NATJECATELJ);
        when(korisnikRepository.save(any(Korisnik.class))).thenAnswer(i -> (Korisnik) i.getArguments()[0]);

        // Act
        Korisnik result = korisnikController.addKorisnik(file, dto);

        // Assert
        assertNotNull(result);
        assertEquals(dto.getKorisnickoIme(), result.getKorisnickoIme());
        assertEquals(dto.getIme(), result.getIme());
        assertEquals(dto.getPrezime(), result.getPrezime());
        assertEquals(dto.getEmail(), result.getEmail());
        assertEquals(dto.getRequestedUloga(), result.getRequestedUloga());
        assertNotNull(result.getLozinka());
        assertNotNull(result.getFotografija());
    }

    @Test
    public void test_throw_exception_if_username_already_exists() {
        // Arrange
        String existingUsername = "MoranaZibar";
        MultipartFile file = new MockMultipartFile(
                "image", "test.jpg", "image/jpeg", "test".getBytes());
        RegisterKorisnikDTO dto = new RegisterKorisnikDTO();
        dto.setKorisnickoIme(existingUsername);
        dto.setIme("Morana");
        dto.setPrezime("Zibar");
        dto.setLozinka("kresojezivcenjak");
        dto.setEmail("morana@zibar.hr");
        dto.setRequestedUloga(Uloga.NATJECATELJ);
        when(korisnikRepository.findByKorisnickoIme(existingUsername)).thenReturn(Optional.of(mock(Korisnik.class)));

        // Act & Assert
        assertThrows(RequestDeniedException.class, () -> {
            korisnikController.addKorisnik(file, dto);
        });
    }

    @Test
    public void test_confirmRequest_success() {
        // Arrange
        Authentication auth = new UsernamePasswordAuthenticationToken("admin", "adminSifra", Collections.singleton(new SimpleGrantedAuthority("ADMIN")));
        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(auth);

        Korisnik korisnik = new Korisnik();
        korisnik.setRequestedUloga(Uloga.VODITELJ);
        korisnik.setKorisnickoIme("MladenVukorepa");
        korisnik.setEmail("ScarlettOHara@gmail.com");
        when(korisnikRepository.findByKorisnickoIme("MladenVukorepa")).thenReturn(Optional.of(korisnik));

        // Act
        ResponseEntity<?> response = korisnikController.confirmRequest("MladenVukorepa");

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }


}
