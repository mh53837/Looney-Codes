package hr.fer.progi.looneycodes.BytePit.api.controller;

import hr.fer.progi.looneycodes.BytePit.api.model.Korisnik;
import hr.fer.progi.looneycodes.BytePit.api.model.Rjesenje;
import hr.fer.progi.looneycodes.BytePit.api.model.TestniPrimjer;
import hr.fer.progi.looneycodes.BytePit.api.model.Zadatak;
import hr.fer.progi.looneycodes.BytePit.service.KorisnikService;
import hr.fer.progi.looneycodes.BytePit.service.RjesenjeService;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.LinkedList;
import java.util.List;
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

    
    /*
     * Ruta za dohvaćanje svih rješenja
     */
    @GetMapping("/all")
    public List<Rjesenje> listAll() {
        return rjesenjeService.listAll();
    }

    /*
     * Ruta za dohvaćanje svih rješenja jednog korisnika.
     */
    @GetMapping("get/natjecatelj/{korisnickoIme}")
    public List<Rjesenje> getRjesenjeByKorisnikId(@PathVariable String korisnickoIme) {
        Optional<Korisnik> korisnik = korisnikService.getKorisnik(korisnickoIme);

        Korisnik natjecatelj = null;
        if (korisnik.isPresent()) {
            natjecatelj = korisnik.get();
        }

        return rjesenjeService.findByRjesenjeIdNatjecatelj(natjecatelj);
    }
    /*
     * Ruta za upload novog rješenja korisnika.
     * Rezultat se vraća u obliku EvaluationResultDTO
     *
     * @see EvaluationResultDTO
     */
    @PostMapping("/upload")
    public EvaluationResultDTO uploadSolution(@RequestBody SubmissionDTO dto) throws IOException, InterruptedException {
    	EvaluationResultDTO resultDTO = rjesenjeService.evaluate(dto);

      // TODO fali createRjesenje funkcija u rjesenjeDTO ???
      // dodati rjesenje u bazu

      // vrati resultDTO nakon kaj si ga spremil
      return resultDTO;
    }
    
    
    /*
     * Pomoćna ruta za ručni pregled evaluacije pojedinog testnog primjera.
     */
    @GetMapping("/get/byToken/{token}")
    public String lastSubmission(@PathVariable String token) throws IOException, InterruptedException {
    	HttpRequest request = HttpRequest.newBuilder()
    			.uri(URI.create("https://judge0-ce.p.rapidapi.com/submissions/"+token+"?base64_encoded=true&fields=*"))
    			.header("X-RapidAPI-Key", "960c7317cdmsh4407a23d8b9aaabp1f99d8jsnd618f8fb2e8c")
    			.header("X-RapidAPI-Host", "judge0-ce.p.rapidapi.com")
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
