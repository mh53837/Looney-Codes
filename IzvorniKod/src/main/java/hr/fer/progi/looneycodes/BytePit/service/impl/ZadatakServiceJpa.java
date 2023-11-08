package hr.fer.progi.looneycodes.BytePit.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import hr.fer.progi.looneycodes.BytePit.api.model.Korisnik;
import hr.fer.progi.looneycodes.BytePit.api.model.Natjecanje;
import hr.fer.progi.looneycodes.BytePit.api.model.Zadatak;
import hr.fer.progi.looneycodes.BytePit.api.repository.ZadatakRepository;
import hr.fer.progi.looneycodes.BytePit.service.ZadatakService;

@Service
public class ZadatakServiceJpa implements ZadatakService {
	@Autowired
	ZadatakRepository zadatakRepo;
	
	@Override
	public List<Zadatak> listAllJavniZadatak() {
		return zadatakRepo.findAllJavniZadatak();
	}

	@Override
	public Optional<Zadatak> fetch(Integer Id) {
		return zadatakRepo.findById(Id);
	}

	@Override
	public Zadatak createZadatak(Zadatak zadatak) {
		Assert.isNull(zadatak.getZadatakId(), "ZadatakId mora biti null prilikom stvaranja zadatka");
		return zadatakRepo.save(zadatak);
	}

	@Override
	public Zadatak updateZadatak(Zadatak zadatak) {
		Integer zadatakId = Objects.requireNonNull(zadatak.getZadatakId());

		Optional<Zadatak> stariZadatak = zadatakRepo.findById(zadatakId);
		if(stariZadatak.isEmpty())
		      throw new IllegalArgumentException("Zadatak s id-em: " + zadatakId + " ne postoji!");
		return zadatakRepo.save(zadatak);
	}

	@Override
	public List<Zadatak> listAllZadaciNatjecanje(Natjecanje natjecanje) {
		return zadatakRepo.findByNatjecanje(natjecanje);
	}

	@Override
	public List<Zadatak> listAllZadaciVoditelj(Korisnik voditelj) {
		return zadatakRepo.findByVoditelj(voditelj);
	}

}
