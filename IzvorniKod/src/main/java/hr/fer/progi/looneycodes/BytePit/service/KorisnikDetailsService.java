package hr.fer.progi.looneycodes.BytePit.service;

// local imports
import hr.fer.progi.looneycodes.BytePit.api.model.Uloga;

// spring-boot imports
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

// java imports
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * U ovoj klasi definiramo koje rute smije korisnik koristiti preko liste autoriteta.
 * @see GrantedAuthorities
 */
@Service
public class KorisnikDetailsService implements UserDetailsService {
  @Autowired
  KorisnikService korisnikService;

  @Override
  public UserDetails loadUserByUsername(String username){
    String passHash = korisnikService.getPassHash(username)
                                     .orElseThrow(()
                                       -> new RequestDeniedException("Can't find password for user"));
    return new User(username, passHash, authorities(username));
  }

  private List<GrantedAuthority> authorities(String username){
    List<GrantedAuthority> auth = new LinkedList<>();

    // svi imaju UNREGISTERED ovlasti + jos nekaj onda ak postoji korisnik
    auth.add(new SimpleGrantedAuthority("UNREGISTERED"));

    Optional<Uloga> u = korisnikService.getRole(username);
    if(korisnikService.getRole(username).isEmpty())
      return auth;

    // dodaj ulogu korisnika jos
    auth.add(new SimpleGrantedAuthority(u.get().name()));
    return auth;
  }
}
