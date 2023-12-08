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
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Service
public class RjesenjeServiceJpa implements RjesenjeService {

    @Autowired
    RjesenjeRepository rjesenjeRepository;

    @Autowired
    TestniPrimjerRepository testRepository;
    
    @Autowired
    ZadatakRepository zadatakRepository;
    
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
		Zadatak zadatak = zadatakRepository.getById(dto.getZadatakId());
		
		int count = 0, correct = 0;
		
		for(TestniPrimjer test : testRepository.findByTestniPrimjerIdZadatak(zadatak)) {
			String json = "{"
                    + "\"source_code\":\"" + dto.getProgramskiKod().replace("\n", "\\n") + "\","
                    + "\"language_id\":\"52\","
                    + "\"stdin\":\"" + test.getUlazniPodaci() + "\","
                    + "\"expected_output\":\"" + test.getIzlazniPodaci() + "\""
                    + "}";
			HttpRequest request = HttpRequest.newBuilder()
	    			.uri(URI.create("https://judge0-ce.p.rapidapi.com/submissions?base64_encoded=false&fields=*"))
	    			.header("content-type", "application/json")
	    			.header("Content-Type", "application/json")
	    			.header("X-RapidAPI-Key", "960c7317cdmsh4407a23d8b9aaabp1f99d8jsnd618f8fb2e8c")
	    			.header("X-RapidAPI-Host", "judge0-ce.p.rapidapi.com")
	    			.method("POST", HttpRequest.BodyPublishers.ofString(json))
	    			.build();
			
			HttpResponse<String> response = null;
			try {
				response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}
	    	String token = getToken(response.body());
	    	
	    	
		    //TODO pametnije napisati ovo čekanje procesuiranja
	    	int status = 0;  	
	    	while(status < 3) {
	    		status = getEvaluation(token);
	    	}
	    	if(status == 3) {
	    		correct++;
	    	}
	    	count++;
	    	
		}
		System.out.println(correct/count*100 + "% correct");
    	return correct/count*100;
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
            e.printStackTrace();
        }
		return null;
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
    			.header("X-RapidAPI-Key", "960c7317cdmsh4407a23d8b9aaabp1f99d8jsnd618f8fb2e8c")
    			.header("X-RapidAPI-Host", "judge0-ce.p.rapidapi.com")
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
            String stdout = jsonNode.get("stdout").asText();
            String expectedOutput = jsonNode.get("expected_output").asText();
            Integer status = Integer.parseInt(jsonNode.get("status_id").asText());
            
            // Ispis rezultata
            System.out.println("stdout: " + stdout);
            System.out.println("expected_output: " + expectedOutput);
            System.out.println("status: " + status);

            return status;
            
           
        } catch (Exception e) {
            e.printStackTrace();
        }
		return 0;
	}
}