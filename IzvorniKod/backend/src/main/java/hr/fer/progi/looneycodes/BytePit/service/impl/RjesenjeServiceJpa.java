package hr.fer.progi.looneycodes.BytePit.service.impl;

import hr.fer.progi.looneycodes.BytePit.api.controller.SubmissionDTO;
import hr.fer.progi.looneycodes.BytePit.api.model.Korisnik;
import hr.fer.progi.looneycodes.BytePit.api.model.Rjesenje;
import hr.fer.progi.looneycodes.BytePit.api.model.TestniPrimjer;
import hr.fer.progi.looneycodes.BytePit.api.model.Zadatak;
import hr.fer.progi.looneycodes.BytePit.api.repository.RjesenjeRepository;
import hr.fer.progi.looneycodes.BytePit.api.repository.TestniPrimjerRepository;
import hr.fer.progi.looneycodes.BytePit.api.repository.ZadatakRepository;
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
import java.util.Queue;
import java.util.LinkedList;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

@Service
public class RjesenjeServiceJpa implements RjesenjeService {

    @Autowired
    RjesenjeRepository rjesenjeRepository;

    @Autowired
    TestniPrimjerRepository testRepository;
    
    @Autowired
    ZadatakRepository zadatakRepository;

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
    
      
    /*
     * TODO 
     * 	- pretvoriti dobivene podatke u Rjesenje i spremiti ga
     * 	- dodati u json kojim se zove evaluacija time_limit
     *  - riješiti ostale TODO napomene u funkciji
     *  ???? kaj bi sve trebalo vratti na front? dal nam trebaju podaci zakaj, de i kak pada? kak to strukturirati - možda novi DTO s detaljima evaluacije rješenja?
     */
	@Override
	public double evaluate(SubmissionDTO dto) {
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
            "language_id" : 52,
            "stdin" : "%s",
            "expected_output" : "%s"
          }
          """,
          dto.getProgramskiKod().replace("\n", "\\n"),
          test.getUlazniPodaci(), test.getIzlazniPodaci()
      );
      System.out.println("Sending json: " + json);
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

      // break;
		}
  
    // provjeri rezultate
    while(!tokenQueue.isEmpty()) {
      String curr = tokenQueue.remove();
      int status = getEvaluation(curr);

      // nije gotovo
      if(status < 3) {
        tokenQueue.add(curr);
      }
      else if(status == 3) {
        correct++;
      }
      // ak je krivo, ne brojimo nist..
    }

    // debug output
		System.out.println(correct/total*100 + "% correct");
    return total != 0? correct/total*100 : 0;
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
	 * TODO 
	 * 	- riješiti base64 dekodiranje
	 *  - bumo i compiler output vraćali?
	 */
	private int getEvaluation(String token) {
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    String jsonString =	response.body();

    // Koristimo Jackson ObjectMapper za parsiranje JSON-a
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      // Pretvorba JSON stringa u Jackson JsonNode
      JsonNode jsonNode = objectMapper.readTree(jsonString);

      // Dobivanje vrijednosti za stdout, expected_output i status
      int status = jsonNode.get("status_id").asInt();
      if(status < 3)
        return status;

      String stdout = jsonNode.get("stdout").asText();
      String expectedOutput = jsonNode.get("expected_output").asText();
      // Ispis rezultata
      System.out.println("stdout: " + stdout);
      System.out.println("expected_output: " + expectedOutput);
      System.out.println("status: " + status);

      return status;
    } catch (Exception e) {
      e.printStackTrace();
    }

    return -1;
	}
}
