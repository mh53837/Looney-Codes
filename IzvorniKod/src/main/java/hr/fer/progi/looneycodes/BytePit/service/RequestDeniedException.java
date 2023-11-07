package hr.fer.progi.looneycodes.BytePit.service;

// spring-boot imports
import org.springframework.web.bind.annotation.ResponseStatus;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * Iznimka koju posaljemo u slucaju da je korisnik kriv, a ne sustav.
 * Client Side Error
 */
@ResponseStatus(BAD_REQUEST)
public class RequestDeniedException extends RuntimeException {
  public RequestDeniedException(String msg){
    super(msg);
  }
}
