package hr.fer.progi.looneycodes.BytePit.api.controller;

// local import
import hr.fer.progi.looneycodes.BytePit.service.EmailService;
import hr.fer.progi.looneycodes.BytePit.service.KorisnikService;
import hr.fer.progi.looneycodes.BytePit.service.RequestDeniedException;
import hr.fer.progi.looneycodes.BytePit.service.NotFoundException;
import hr.fer.progi.looneycodes.BytePit.api.model.Korisnik;

// spring-boot imports
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


// java imports
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;
import java.io.IOException;


/**
 * Kontroler koji sluzi kao pristup metodama vezane uz Korisnik entitet.
 * Ovdje su definirane adrese preko kojih mozemo kroz browser/postman pristupati nasim servisima.
 * @see KorisnikService
 * @see Korisnik
 */
@RestController
@RequestMapping("/api/user")
public class KorisnikController{
  @Autowired
  private KorisnikService korisnikService;

  @Autowired
  private EmailService mailService;

  @Autowired
  private PasswordEncoder encoder;

  @Value("${BytePit.domain}")
  private String domena;
  /*
   * Izlistaj sve registrirane korisnike koji imaju potvrdeni account.
   * @return lista svih korisnika koji imaju atribut confirmedEmail = true
   */
  @GetMapping("/all")
  public List<KorisnikInfoDTO> listAll(){
    return korisnikService.listAllVerified();
  }
  /*
   * Izlistaj sve registrirane korisnike (za admina!).
   * @return lista svih korisnika
   */
  @GetMapping("/allAdmin")
  @Secured("ADMIN")
  public List<Korisnik> listAllAdmin(){
    return korisnikService.listAllAdmin();
  }
  /**
   * Izlistaj sve korisnike kojima je trenutna uloga razlicita od zatrazene.
   * @return lista svih korisnika s atributima uloga != requestedUloga
   */
  @GetMapping("/listRequested")
  @Secured("ADMIN")
  public List<KorisnikInfoDTO> listRequested(){
    return korisnikService.listAllRequested();
  }
  /**
   * Metoda kojom admini potvrduju drugima uloge.
   * @param korisnickoIme korisnicko ime korisnika kojeg potvrdujemo
   * @exception NotFoundException ako korisnik s zadanim imenom ne postoji u bazi
   * @exception RequestDeniedException ako je admin vec potvrdio ulogu
   * @return ResponseEntity s kodom 200 ak je sve ok
   */
  @PostMapping("/confirmRequest/{korisnickoIme}")
  @Secured("ADMIN")
  public ResponseEntity<?> confirmRequest(@PathVariable String korisnickoIme){
    Korisnik korisnik = korisnikService.getKorisnik(korisnickoIme)
                                       .orElseThrow(()
                                          -> new NotFoundException("Korisnik s korisnickim imenom: " + 
                                                                    korisnickoIme + " ne postoji!"));

    if(korisnik.getRequestedUloga() == korisnik.getUloga())
      throw new RequestDeniedException("Admin je vec potvrdio korisnikovu ulogu!");

    return ResponseEntity.ok(korisnikService.confirmRequest(korisnik));
  }
  /**
   * Vrati korisnika na temelju id-a
   * @return bilo koji korisnik koji je zapisan u bazi s zadanim id-em
   * @return Optional.empty() ako ne postoji korisnik s tim id-em
   */
  @GetMapping("/get/{id}")
  @Secured("ADMIN")
  public Optional<Korisnik> getKorisnik(@PathVariable int id){
    return korisnikService.fetch(id);
  }
  
  /**
   * Vrati korisnika na temelju korisnickog imena
   * @return bilo koji korisnik koji je zapisan u bazi s zadanim korisnickim imenom
   * @return Optional.empty() ako ne postoji korisnik s tim korisnickim imenom
   */
  @GetMapping("/profile/{korisnickoIme}")
  public Optional<KorisnikInfoDTO> profileKorisnik(@PathVariable String korisnickoIme){
    Korisnik korisnik = korisnikService.getKorisnik(korisnickoIme)
    			.orElseThrow(() -> new NotFoundException("Korisnik s korisnickim imenom: " + 
            korisnickoIme + " ne postoji!"));
    return Optional.of(new KorisnikInfoDTO(korisnik));
  }
  

  /**
   * Registriraj novog korisnika, nakon cega on mora potvrditi registraciju
   *
   * u slucaju da je zatrazena uloga == Uloga.VODITELJ postavljamo ga da bude imao
   * ovlasti natjecatelja tak dugo dok ga admin ne potvrdi!
   *
   * @param dto instanca RegisterKorisnikDTO objekta s postavljenim podacima za registraciju
   * username, password, email, requestedUloga
   * @return referenca na novi zapis korisnika u bazi
   */
  @PostMapping("/register")
  public Korisnik addKorisnik(@RequestPart("image") MultipartFile file, @RequestPart("userData")  RegisterKorisnikDTO dto){


    try {
    	int extensionIndex = file.getOriginalFilename().lastIndexOf('.');
    	if (extensionIndex < 1) {
    		dto.setFotografija(null);
    	} else {
    		String extension = file.getOriginalFilename().substring(extensionIndex);

        	if (!List.of(".jpg", ".jpeg", ".png").contains(extension)) 
        		throw new RequestDeniedException("Format slike nije podržan! Podržani formati su .jpg, .jpeg, .png");
        	
            Path savePath = Path.of("./src/main/resources/profilneSlike").resolve(dto.getKorisnickoIme() +  extension);
            file.transferTo(savePath);
            dto.setFotografija(savePath.toString());
    	}
    	
    } catch (IOException e) {
        throw new RequestDeniedException("Nije uspio upload slike");
    }

    // daj mu id
    Korisnik korisnik = korisnikService.createKorisnik(dto);
    // salji mail za potvrdu registracije
    mailService.sendMail(korisnik.getEmail(), 
                          "DO NOT REPLY: Account confirmation for BytePit",
                          "Verify your BytePit account using this link: " +
                          domena + "/user/confirmEmail/" + korisnik.getKorisnikId());
    return korisnik;
  }
  /**
   * Ruta koja sluzi za login korisnika
   *
   * Moguci ishodi: 200 OK, RequestDenied ako user ne postoji ili FORBIDDEN ako nije potvrden
   *
   * @param dto objekt koji sadrzi username i password
   * @exception RequestDeniedException ako imamo krive podatke ili nepotvrdeni account
   */
  @PostMapping("/login")
  public KorisnikInfoDTO loginKorisnik(@RequestBody LoginKorisnikDTO dto) {
    Korisnik korisnik = korisnikService.getKorisnik(dto.getKorisnickoIme())
                                       .orElseThrow(()
                                          -> new RequestDeniedException("Username not found!"));

    if(!encoder.matches(dto.getLozinka(), korisnik.getLozinka()))
      throw new RequestDeniedException("Wrong password!");

    if (korisnik.isConfirmedEmail()) {
    	return new KorisnikInfoDTO(korisnik);
    } else {
    	throw new AccessDeniedException("Korisnik nije potvrdio email!");
    }
  }
  /**
   * Potvrdi email za novog korisnika.
   * @param id id korisnika kao path variable (automatski dobije za rutu link na mail)
   * @return ResponseEntity s kodom 200 ak je sve ok
   * @exception NotFoundException u slucaju da id ne postoji
   * @exception RequestDeniedException u slucaju da je korisnik vec potvrdio adresu
   */
  @GetMapping("/confirmEmail/{id}")
  public ResponseEntity<?> confirmEmail(@PathVariable int id){
    Optional<Korisnik> korisnik = korisnikService.fetch(id);

    if(korisnik.isEmpty())
      throw new NotFoundException("Korisnik s id-em: " + id + " ne postoji!");
    if(korisnik.get().isConfirmedEmail())
      throw new RequestDeniedException("Korisnik je vec potvrdio svoju email adresu!");

    return ResponseEntity.ok(korisnikService.confirmMail(korisnik.get()));
  }
  /**
   * Azuriraj korisnicke podatke za odredenog korisnika.
   * @param korisnickoIme salje se kao path variable
   * @param dto samo atributi koje zelimo mijenjati se postave u dto, ostalo se ignorira automatski
   * @param user trenutno autentificirani korisnik, radi sigurnosti provjeravamo da ne mijenja tude podatke
   * @exception IllegalArgumentException u slucaju da pokusavamo mijenjati tude podatke (a da nismo ADMIN!)
   * @exception AccessDeniedException u slucaju da nismo ulogirani
   * @return referenca na azurirani zapis u bazi
   */
  @PostMapping("/update/{korisnickoIme}")
  public Korisnik updateKorisnik(@PathVariable String korisnickoIme, @RequestBody RegisterKorisnikDTO dto, @AuthenticationPrincipal UserDetails user){
    if(Objects.isNull(user))
      throw new AccessDeniedException("You must be logged in for that!");

    if(!user.getUsername().equals(korisnickoIme)
        && !user.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN")))
      throw new IllegalStateException("Krivi korisnik!");

    dto.setKorisnickoIme(korisnickoIme);
    return korisnikService.updateKorisnik(dto);
  }
  /**
   * Dohvati profilnu sliku korisnika
   * @param username korisnicko ime korisnika za kojeg trazimo profilnu sliku
   * @return profilna slika korisnika
   */
  @GetMapping("/image/{username}")
  public ResponseEntity<byte[]> getProfilePicture(@PathVariable("username") String username) {
    Pair<byte[], MediaType> picture = korisnikService.getProfilePicture(username);
    return ResponseEntity.ok()
            .contentType(picture.getSecond())
            .body(picture.getFirst());
  }
}
