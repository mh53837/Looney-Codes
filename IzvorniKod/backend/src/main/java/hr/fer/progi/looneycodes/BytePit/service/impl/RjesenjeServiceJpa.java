package hr.fer.progi.looneycodes.BytePit.service.impl;

import hr.fer.progi.looneycodes.BytePit.api.controller.EvaluationResultDTO;
import hr.fer.progi.looneycodes.BytePit.api.controller.SubmissionDTO;
import hr.fer.progi.looneycodes.BytePit.api.model.*;
import hr.fer.progi.looneycodes.BytePit.api.repository.*;
import hr.fer.progi.looneycodes.BytePit.service.RjesenjeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.LinkedList;
import java.util.OptionalInt;
import java.sql.Timestamp;
import java.time.Instant;

@Service
public class RjesenjeServiceJpa implements RjesenjeService {

    @Autowired
    RjesenjeRepository rjesenjeRepository;

    @Autowired
    TestniPrimjerRepository testRepository;
    
    @Autowired
    ZadatakRepository zadatakRepository;

    @Autowired
    KorisnikRepository korisnikRepository;

    @Autowired
    VirtualnoNatjecanjeRepository virtualnoNatjecanjeRepository;
    @Autowired
    NatjecanjeRepository natjecanjeRepository;

    @Value("${BytePit.rapidApiKey}")
    private String apiKey;
    @Value("${BytePit.rapidApiHost}")
    private String apiHost;
    
    @Override
    public List<Rjesenje> listAll() {
        return rjesenjeRepository.findAll();
    }

    @Override
    public List<Rjesenje> findByRjesenjeIdNatjecatelj(Korisnik natjecatelj) {
        return rjesenjeRepository.findByRjesenjeIdNatjecatelj(natjecatelj);
    }

    @Override
    public List<Rjesenje> findByNatjecateljAndNatjecanje(Korisnik natjecatelj, Natjecanje natjecanje){
        return rjesenjeRepository.findByNatjecateljAndNatjecanje(natjecatelj, natjecanje);
    }

    @Override
    public List<Rjesenje> findByNatjecanjeAndZadatak(Nadmetanje natjecanje, Zadatak zadatak) {
        return rjesenjeRepository.findByNatjecanjeAndZadatak(natjecanje, zadatak);
    }

    @Override
    public List<Rjesenje> findByNatjecateljAndZadatak(Korisnik natjecatelj, Zadatak zadatak) {
        return rjesenjeRepository.findByNatjecateljAndZadatak(natjecatelj, zadatak);
    }

    @Override
    public List<Rjesenje> findByNatjecanjeId(Integer natjecanjeId) {
        return rjesenjeRepository.findByNatjecanjeId(natjecanjeId);
    }
    @Override
    public Rjesenje add(EvaluationResultDTO dto, String korisnickoIme, Integer zadatakId, String programskiKod, OptionalInt nadmetanjeId) {
      Optional<Korisnik> natjecatelj = korisnikRepository.findByKorisnickoIme(korisnickoIme);
      Optional<Zadatak> zadatak = zadatakRepository.findById(zadatakId);

      if(natjecatelj.isEmpty())
        throw new IllegalArgumentException("Natjecatelj ne postoji!");
      if(zadatak.isEmpty())
        throw new IllegalArgumentException("Zadatak ne postoji!");

      OptionalInt max = rjesenjeRepository.findByRjesenjeIdNatjecatelj(natjecatelj.get())
                                          .stream()
                                          .mapToInt(x -> x.getRjesenjeId().getRjesenjeRb())
                                          .max();

      int rjesenjeRb = 0;
      if(max.isPresent())
        rjesenjeRb = max.getAsInt() + 1;

      RjesenjeKey rjesenjeId = new RjesenjeKey(rjesenjeRb, natjecatelj.get(), zadatak.get());
      Nadmetanje nadmetanje = null;
      if (nadmetanjeId != null && nadmetanjeId.isPresent()) {
        if(natjecanjeRepository.findById(nadmetanjeId.getAsInt()).isPresent())
          nadmetanje = natjecanjeRepository.findById(nadmetanjeId.getAsInt()).get();
        else if(virtualnoNatjecanjeRepository.findById(nadmetanjeId.getAsInt()).isPresent())
          nadmetanje = virtualnoNatjecanjeRepository.findById(nadmetanjeId.getAsInt()).get();
      }

      Rjesenje rjesenje = new Rjesenje(rjesenjeId,
                                      Timestamp.from(Instant.now()),
                                      dto.getRezultat(),
                                      programskiKod,
                                      nadmetanje
                                      );
  
      return rjesenjeRepository.save(rjesenje);
    }


    /*
     * Metoda za evaluaciju.
     *
     * @see /IzvorniKod/backend/evaluacija_readme.md
     */
	@Override
	public EvaluationResultDTO evaluate(SubmissionDTO dto) {
		Zadatak zadatak = zadatakRepository.getReferenceById(dto.getZadatakId());
		
    List<TestniPrimjer> testniPrimjeri = testRepository.findByTestniPrimjerIdZadatak(zadatak);
		int total = testniPrimjeri.size(), correct = 0;
		
    // salji request za svaki test primjer - posle provjeravamo status
    Queue<String> tokenQueue = new LinkedList<>();
		for(TestniPrimjer test : testniPrimjeri) {
			String json = String.format(
          """
          {
            "source_code" : "%s",
            "language_id" : 12,
            "stdin" : "%s",
            "expected_output" : "%s",
            "cpu_time_limit" : %d
          }
          """,
          dto.getProgramskiKod().replace("\n", "\\n").replace("\"", "\\\""),
          test.getUlazniPodaci(), test.getIzlazniPodaci(),
          zadatak.getVremenskoOgranicenje()
      );
			HttpRequest request = HttpRequest.newBuilder()
	    			.uri(URI.create("https://judge0-ce.p.rapidapi.com/submissions?base64_encoded=false&fields=*"))
	    			.header("content-type", "application/json")
	    			.header("Content-Type", "application/json")
	    			.header("X-RapidAPI-Key", apiKey)
	    			.header("X-RapidAPI-Host", apiHost)
	    			.method("POST", HttpRequest.BodyPublishers.ofString(json))
	    			.build();
			
			HttpResponse<String> response = null;
			try {
				response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}
      String token = getToken(response.body());
      
      //zapamti token za posle dok bumo provjeravali rezultate evaluacije
      tokenQueue.add(token);
		}
  
    // provjeri rezultate
    EvaluationResultDTO out = new EvaluationResultDTO();
    while(!tokenQueue.isEmpty()) {
      String curr = tokenQueue.remove();
      String stderr = new String();
      int status = getEvaluationStatus(curr, stderr);

      // nije gotovo
      if(status < 3) {
        tokenQueue.add(curr);
        continue;
      }
      // greska!
      else if(status == 6) {
        out.setCompilerOutput(Optional.of(stderr));
        out.setTests(null);
        break;
      }

      // tocno
      if(status == 3) {
        correct++;
      }

      out.addStatus(status);
    }

    if(out.getTests() == null)
      return out;

    out.setRezultat(total != 0? (double) correct / total : 0);
    return out;
	}
	
	/*
	 * Vadi string tokena iz response POST zahtjeva na api.
	 */
	private String getToken(String jsonString) {
		ObjectMapper objectMapper = new ObjectMapper();
      try {
          // Pretvorba JSON stringa u Jackson JsonNode
          JsonNode jsonNode = objectMapper.readTree(jsonString);

          // Dobivanje vrijednosti za stdout, expected_output i status
          String token = jsonNode.get("token").asText();

          // Ispis rezultata
          System.out.println("token: " + token);
          return token;

      } catch (Exception e) {
        throw new RuntimeException(jsonString);
      }
	}
	
	/*
	 * Vadi rezultate za pojedini testni primjer prema dobivenom tokenu.
   * Ako se radi o gresci u kompajliranju, poruku o toj gresci zapisujemo u parametar "stderr"
	 */
	private int getEvaluationStatus(String token, String stderr) {
		HttpRequest request = HttpRequest.newBuilder()
    			.uri(URI.create("https://judge0-ce.p.rapidapi.com/submissions/"+token+"?base64_encoded=true&fields=*"))
    			.header("X-RapidAPI-Key", apiKey)
    			.header("X-RapidAPI-Host", apiHost)
    			.method("GET", HttpRequest.BodyPublishers.noBody())
    			.build();
    	HttpResponse<String> response = null;
		try {
			response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
		} catch (IOException | InterruptedException e) {
      return -1; // nema smisla obradivati ak nemamo odgovor...
		}
    String jsonString =	response.body();

    // Koristimo Jackson ObjectMapper za parsiranje JSON-a
    ObjectMapper objectMapper = new ObjectMapper();
    int status = -1;
    try {
      // Pretvorba JSON stringa u Jackson JsonNode
      JsonNode jsonNode = objectMapper.readTree(jsonString);

      // Dobivanje vrijednosti za status
      status = jsonNode.get("status_id").asInt();

      if(status == 6) {
        stderr = jsonNode.get("compile_output").asText();
      }
    } catch (Exception e) {
      return -1;
    }

    return status;
	}

	@Override
	public List<Rjesenje> findByNatjecanjeAndZadatakAndNatjecatelj(Natjecanje natjecanje, Zadatak zadatak,
			Korisnik natjecatelj) {
		return null;
	}

	@Override
	public Optional<Rjesenje> fetch(Integer redniBroj, Integer zadatakId, String korisnickoIme) {
		RjesenjeKey id = new RjesenjeKey(redniBroj, 
				korisnikRepository.findByKorisnickoIme(korisnickoIme).get(),
				zadatakRepository.findById(zadatakId).get());
		return rjesenjeRepository.findById(id);
	}

	@Override
	public boolean solved(Integer zadatakId, String korisnickoIme) {
		Optional<Korisnik> natjecatelj = korisnikRepository.findByKorisnickoIme(korisnickoIme);
		Optional<Zadatak> zadatak = zadatakRepository.findById(zadatakId);
		if(natjecatelj.isPresent() && zadatak.isPresent()) {
			List<Rjesenje> rjesenja = rjesenjeRepository.findByNatjecateljAndZadatak(natjecatelj.get(), zadatak.get());
			if(rjesenja.size() > 0 && 
					rjesenja.stream().filter(r -> r.getBrojTocnihPrimjera() == 1).toList().size() > 0)
				return true;
		}
		return false;
	}
}
