package hr.fer.progi.looneycodes.BytePit.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.fer.progi.looneycodes.BytePit.api.model.TestniPrimjer;
import hr.fer.progi.looneycodes.BytePit.api.model.Zadatak;
import hr.fer.progi.looneycodes.BytePit.api.repository.TestniPrimjerRepository;
import hr.fer.progi.looneycodes.BytePit.service.TestniPrimjerService;

@Service
public class TestniPrimjerServiceJpa implements TestniPrimjerService {
	@Autowired
	TestniPrimjerRepository testRepo;
	
	@Override 
	public List<TestniPrimjer> listAllByZadatak(Zadatak zadatak) {
		return testRepo.findByTestniPrimjerIdZadatak(zadatak);
	}

	@Override
	public TestniPrimjer add(TestniPrimjer test) {
		Integer max;
		try {
			max = testRepo.findByTestniPrimjerIdZadatak(test.getTestniPrimjerId().getZadatak()).stream().mapToInt(x -> x.getTestniPrimjerId().getTestniPrimjerRb()).max().getAsInt();
		} catch (Exception e){
			max = 0;
		} 
	
		test.getTestniPrimjerId().setTestniPrimjerRb(max+1);;
		return testRepo.save(test); 
	}

}
