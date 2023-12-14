package hr.fer.progi.looneycodes.BytePit.api.controller;

import hr.fer.progi.looneycodes.BytePit.api.model.Korisnik;
import hr.fer.progi.looneycodes.BytePit.api.model.Natjecanje;
import hr.fer.progi.looneycodes.BytePit.api.model.Rjesenje;
import hr.fer.progi.looneycodes.BytePit.api.model.Zadatak;
import hr.fer.progi.looneycodes.BytePit.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 *
 * Kontroler koji služi kao pristup metodama vezane uz Rjesenje entitete.
 * Ovdje su definirane adrese preko kojih mozemo kroz browser/postman pristupati našim servisima.
 *
 */

@RestController
@RequestMapping("/api/solutions")
public class RjesenjeController {
    @Autowired
    private RjesenjeService rjesenjeService;

    @Autowired
    private KorisnikService korisnikService;

    @Autowired
    private NatjecanjeService natjecanjeService;

    @Autowired
    private ZadatakService zadatakService;

    @Value("${BytePit.rapidApiKey}")
    private String apiKey;
    @Value("${BytePit.rapidApiHost}")
    private String apiHost;

    /*
     * Ruta za dohvaćanje svih rješenja
     */
    @GetMapping("/all")
    @Secured("ADMIN")
    public List<Rjesenje> listAll() {
        return rjesenjeService.listAll();
    }

    /*
     * Ruta za dohvaćanje svih rješenja jednog korisnika.
     */
    @GetMapping("get/natjecatelj/{korisnickoIme}")
    public List<Rjesenje> getRjesenjeByKorisnikId(@PathVariable String korisnickoIme, @AuthenticationPrincipal UserDetails user) {
        Optional<Korisnik> korisnik = korisnikService.getKorisnik(korisnickoIme);

        if(Objects.isNull(user))
          throw new AccessDeniedException("You must be logged in for that!");
        if(!korisnickoIme.equals(user.getUsername()) &&
            !user.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN")))
          throw new AccessDeniedException("You do not have the authority to access this!");

        if (!korisnik.isPresent()) {
          throw new RequestDeniedException("Korisnik ne postoji!");
        }

        return rjesenjeService.findByRjesenjeIdNatjecatelj(korisnik.get());
    }

    /*
     * Ruta za dohvaćanje svih rješenja jednog korisnika u određenom natjecanju.
     */
    @GetMapping("get/natjecatelj/{korisnickoIme}/{natjecanjeId}")
    public List<Rjesenje> getRjesenjeByKorisnikIdAndNatjecanjeId(@PathVariable String korisnickoIme, @PathVariable Integer natjecanjeId, @AuthenticationPrincipal UserDetails user) {
        Optional<Korisnik> korisnik = korisnikService.getKorisnik(korisnickoIme);
        Natjecanje natjecanje = natjecanjeService.getNatjecanje(natjecanjeId);

        if(Objects.isNull(user))
            throw new AccessDeniedException("You must be logged in for that!");
        if(!korisnickoIme.equals(user.getUsername()) &&
                !user.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN")))
            throw new AccessDeniedException("You do not have the authority to access this!");

        if (!korisnik.isPresent()) {
            throw new RequestDeniedException("Korisnik ne postoji!");
        }

        return rjesenjeService.findByNatjecateljAndNatjecanje(korisnik.get(), natjecanje);
    }

    @GetMapping("get/natjecanje/{natjecanjeId}/{zadatakId}")
    public List<Rjesenje> getRjesenjeByNatjecanjeIdAndZadatakId(@PathVariable Integer natjecanjeId, @PathVariable Integer zadatakId, @AuthenticationPrincipal UserDetails user) {
        Natjecanje natjecanje = natjecanjeService.getNatjecanje(natjecanjeId);
        Zadatak zadatak = zadatakService.fetch(zadatakId);

        return rjesenjeService.findByNatjecanjeAndZadatak(natjecanje, zadatak);
    }

    @GetMapping("get/natjecatelj/{korisnickoIme}/{zadatakId}")
    public List<Rjesenje> getRjesenjeByKorisnikIdAndZadatakId(@PathVariable String korisnickoIme, @PathVariable Integer zadatakId, @AuthenticationPrincipal UserDetails user) {
        Optional<Korisnik> korisnik = korisnikService.getKorisnik(korisnickoIme);
        Zadatak zadatak = zadatakService.fetch(zadatakId);

        if(Objects.isNull(user))
            throw new AccessDeniedException("You must be logged in for that!");
        if(!korisnickoIme.equals(user.getUsername()) &&
                !user.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN")))
            throw new AccessDeniedException("You do not have the authority to access this!");

        if (!korisnik.isPresent()) {
            throw new RequestDeniedException("Korisnik ne postoji!");
        }

        return rjesenjeService.findByNatjecateljAndZadatak(korisnik.get(), zadatak);
    }

    /*
     * Ruta za upload novog rješenja korisnika. Username se automatski postavlja!
     * Rezultat se vraća u obliku EvaluationResultDTO
     *
     * @see EvaluationResultDTO
     */
    @PostMapping("/upload")
    @Secured("NATJECATELJ")
    public EvaluationResultDTO uploadSolution(@RequestBody SubmissionDTO dto, @AuthenticationPrincipal UserDetails user)
                                              throws IOException, InterruptedException
    {
      if(Objects.isNull(user))
        throw new AccessDeniedException("You must be logged in for that!");

      dto.setKorisnickoIme(user.getUsername());
    	EvaluationResultDTO resultDTO = rjesenjeService.evaluate(dto);

      // ne spremamo u bazu ako se nije dalo kompajlirati!
      if(resultDTO.getCompilerOutput().isPresent())
        return resultDTO;

      rjesenjeService.add(resultDTO, dto.getKorisnickoIme(), dto.getZadatakId(), dto.getProgramskiKod());

      // vrati resultDTO nakon kaj si ga spremil
      return resultDTO;
    }
    
    /*
     * Pomoćna ruta za ručni pregled evaluacije pojedinog testnog primjera.
     */
    @GetMapping("/get/byToken/{token}")
    @Secured("ADMIN")
    public String lastSubmission(@PathVariable String token) throws IOException, InterruptedException {
    	HttpRequest request = HttpRequest.newBuilder()
    			.uri(URI.create("https://judge0-ce.p.rapidapi.com/submissions/"+token+"?base64_encoded=true&fields=*"))
    			.header("X-RapidAPI-Key", apiKey)
    			.header("X-RapidAPI-Host", apiHost)
    			.method("GET", HttpRequest.BodyPublishers.noBody())
    			.build();
    	HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
    	String jsonString =	response.body();

        // Koristimo Jackson ObjectMapper za parsiranje JSON-a
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // Pretvorba JSON stringa u Jackson JsonNode
            JsonNode jsonNode = objectMapper.readTree(jsonString);

            // Dobivanje vrijednosti za stdout, expected_output i status
            String stdout = jsonNode.get("stdout").asText();
            String expectedOutput = jsonNode.get("expected_output").asText();
            Integer status = Integer.parseInt(jsonNode.get("status_id").asText());

            // Ispis rezultata
            System.out.println("stdout: " + stdout);
            System.out.println("expected_output: " + expectedOutput);
            System.out.println("status: " + status);

        } catch (Exception e) {
            e.printStackTrace();
        }
    	return response.body();
    }
    
}
