package hr.fer.progi.looneycodes.BytePit.service;

// spring-boot imports
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

// java imports
import java.util.regex.Pattern;

/**
 * Jednostavni servis koji sluzi za slanje mailova, uglavnom kod registracije.
 * @see KorisnikService
 */
@Service
public class EmailService {
  @Autowired
  private JavaMailSender mailSender;

  /**
   * Jednostavna metoda za slanje maila koji ukljucuje samo tekst (nit nam ne treba vise!)
   * @param to primatelj maila
   * @param subject naslov maila (tipa NOREPY:BytePit)
   * @param body tekst koji zelimo poslati
   */
  public void sendMail(String to, String subject, String body){
    // validate mail
    validateMail(to);

    // send it
    SimpleMailMessage poruka = new SimpleMailMessage();
    poruka.setTo(to);
    poruka.setSubject(subject);
    poruka.setText(body);

    mailSender.send(poruka);
  }

  /**
   * Metoda koja se koristi za validaciju formata emaila
   * @exception IllegalArgumentException u slucaju da argument nije valjan email
   */
  private void validateMail(String email){
    // regex pattern za mail naden na internetu (https://www.baeldung.com/java-email-validation-regex)
    String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" 
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

    if(!Pattern.matches(regexPattern, email))
        throw new IllegalArgumentException("Not a valid email!");
  }
}
