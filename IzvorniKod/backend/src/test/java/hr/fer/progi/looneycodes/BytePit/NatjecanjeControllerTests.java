package hr.fer.progi.looneycodes.BytePit;


import hr.fer.progi.looneycodes.BytePit.api.controller.CreateNatjecanjeDTO;
import hr.fer.progi.looneycodes.BytePit.api.controller.NatjecanjeController;

import hr.fer.progi.looneycodes.BytePit.api.model.Natjecanje;
import hr.fer.progi.looneycodes.BytePit.api.repository.NatjecanjeRepository;
import hr.fer.progi.looneycodes.BytePit.service.impl.ScheduledTasks;
import org.junit.jupiter.api.BeforeAll;
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


import java.sql.Timestamp;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class NatjecanjeControllerTests {
    @Autowired
    private NatjecanjeController natjecanjeController;
    @MockBean
    private NatjecanjeRepository natjecanjeRepository;
    @MockBean
    private ScheduledTasks scheduledTasks;
    private static UserDetails userDetails;

    @BeforeAll
    public static void setUp() {
        Authentication auth = new UsernamePasswordAuthenticationToken("buttler", "robin", Collections.singleton(new SimpleGrantedAuthority("VODITELJ")));
        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(auth);
        userDetails = new User("buttler", "robin", Collections.singleton(new SimpleGrantedAuthority("VODITELJ")));
    }

    @Test
    public void test_creates_new_natjecanje_with_valid_input_data() {
        // Arrange
        CreateNatjecanjeDTO natjecanjeDTO = new CreateNatjecanjeDTO();
        natjecanjeDTO.setNazivNatjecanja("testno natjecanje");
        natjecanjeDTO.setPocetakNatjecanja(Timestamp.valueOf("2022-01-01 00:00:00"));
        natjecanjeDTO.setKrajNatjecanja(Timestamp.valueOf("2022-01-02 00:00:00"));
        natjecanjeDTO.setKorisnickoImeVoditelja("buttler");
        when(natjecanjeRepository.save(any(Natjecanje.class))).thenAnswer(i -> (Natjecanje) i.getArguments()[0]);

        // Act
        CreateNatjecanjeDTO result = natjecanjeController.createNatjecanje(natjecanjeDTO, userDetails);

        // Assert
        assertNotNull(result);
        assertEquals(natjecanjeDTO.getNazivNatjecanja(), result.getNazivNatjecanja());
        assertEquals(natjecanjeDTO.getPocetakNatjecanja(), result.getPocetakNatjecanja());
        assertEquals(natjecanjeDTO.getKrajNatjecanja(), result.getKrajNatjecanja());
        assertEquals(natjecanjeDTO.getKorisnickoImeVoditelja(), result.getKorisnickoImeVoditelja());
    }

    @Test
    public void test_throws_exception_if_pocetakNatjecanja_is_after_krajNatjecanja() {
        // Arrange
        CreateNatjecanjeDTO natjecanjeDTO = new CreateNatjecanjeDTO();
        natjecanjeDTO.setNazivNatjecanja("testno natjecanje");
        natjecanjeDTO.setKorisnickoImeVoditelja("buttler");
        natjecanjeDTO.setPocetakNatjecanja(Timestamp.valueOf("2025-01-02 00:00:00"));
        natjecanjeDTO.setKrajNatjecanja(Timestamp.valueOf("2024-01-01 00:00:00"));

        //Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            natjecanjeController.createNatjecanje(natjecanjeDTO, userDetails);
        });
    }

}
