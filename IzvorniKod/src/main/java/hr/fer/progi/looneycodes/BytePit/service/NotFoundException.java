package hr.fer.progi.looneycodes.BytePit.service;

// spring-boot imports
import org.springframework.web.bind.annotation.ResponseStatus;
import static org.springframework.http.HttpStatus.NOT_FOUND;;

/**
 * Iznimka koju posaljemo u slucaju da korisnik zeli pristupiti sadrzaju koji ne postoji.
 * Client Side Error
 */
@ResponseStatus(NOT_FOUND)
public class NotFoundException extends RuntimeException {
  public NotFoundException(String msg){
    super(msg);
  }
}
