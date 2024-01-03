package hr.fer.progi.looneycodes.BytePit.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;
import hr.fer.progi.looneycodes.BytePit.api.controller.ZadatakDTO;
import hr.fer.progi.looneycodes.BytePit.api.model.Korisnik;
import hr.fer.progi.looneycodes.BytePit.api.model.Natjecanje;
import hr.fer.progi.looneycodes.BytePit.api.model.TestniPrimjer;
import hr.fer.progi.looneycodes.BytePit.api.model.Uloga;
import hr.fer.progi.looneycodes.BytePit.api.model.Zadatak;
import hr.fer.progi.looneycodes.BytePit.api.repository.KorisnikRepository;
import hr.fer.progi.looneycodes.BytePit.api.repository.ZadatakRepository;
import hr.fer.progi.looneycodes.BytePit.service.ZadatakService;

@Service
public class ZadatakServiceJpa implements ZadatakService {
	@Autowired
	ZadatakRepository zadatakRepo;
	@Autowired
	KorisnikRepository korisnikRepo;
	@Autowired
	ZadatakRepository testniPrimjerRepo;
	
	@Override
	public List<ZadatakDTO> listAllJavniZadatak() {
		return zadatakRepo.findAllJavniZadatak().stream().map((x) -> new ZadatakDTO(x)).toList();
	}

	@Override
	public Zadatak fetch(Integer id) {
		Optional<Zadatak> zadatak = zadatakRepo.findById(id);
		if(zadatak.isEmpty()) 
			throw new IllegalArgumentException("Zadatak s id-em: " + id + "ne postoji!");
		return zadatak.get();
	}

	@Override
	public Zadatak createZadatak(ZadatakDTO zadatak, String username) {
		Assert.isNull(zadatak.getZadatakId(), "ZadatakId mora biti null prilikom stvaranja zadatka");
		Assert.isNull(zadatak.getVoditelj(), "Voditelj se definira prema korisniku koji je postavio zahtjev!");
		Optional<Korisnik> voditelj = korisnikRepo.findByKorisnickoIme(username);
		if (voditelj.isEmpty())
			throw new IllegalArgumentException("Voditelj < " + username + " > ne postoji!");
		Zadatak novi = new Zadatak(zadatak, voditelj.get());
		novi.setBrojBodova();
		return zadatakRepo.save(novi);
	}

	@Override
	public Zadatak updateZadatak(Integer id, Zadatak dto) {
		Optional<Zadatak> stariZadatak = zadatakRepo.findById(id);
		if(stariZadatak.isEmpty())
		      throw new IllegalArgumentException("Zadatak s id-em: " + id + " ne postoji!");
		Zadatak zadatak = Zadatak.update(stariZadatak.get(), dto);
		zadatak.setBrojBodova();
		return zadatakRepo.save(zadatak);
	}

	@Override
	public List<ZadatakDTO> findByNatjecateljAllSolved(Korisnik natjecatelj) {
		return zadatakRepo.findByNatjecateljAllSolved(natjecatelj).stream().map((x) -> new ZadatakDTO(x)).toList();
	}

	public List<ZadatakDTO> findByNatjecateljAll(Korisnik natjecatelj) {
		return zadatakRepo.findByNatjecateljAll(natjecatelj).stream().map((x) -> new ZadatakDTO(x)).toList();
	}

	@Override
	public List<ZadatakDTO> listAllZadaciNatjecanje(Natjecanje natjecanje) {
		return zadatakRepo.findByNatjecanje(natjecanje).stream().map((x) -> new ZadatakDTO(x)).toList();
	}

	@Override
	public List<ZadatakDTO> listAllZadaciVoditelj(String korisnickoIme) {
		Optional<Korisnik> voditelj = korisnikRepo.findByKorisnickoIme(korisnickoIme);
		if(voditelj.isEmpty()) 
			throw new IllegalArgumentException("Voditelj " + korisnickoIme + " ne postoji!");
		return zadatakRepo.findByVoditelj(voditelj.get()).stream().map((x) -> new ZadatakDTO(x)).toList();
	}

	@Override
	public List<ZadatakDTO> listAll() {
		return zadatakRepo.findAll().stream().map((x) -> new ZadatakDTO(x)).toList();
	}

	@Override
	public List<ZadatakDTO> listAllJavniZadaciVoditelj(String voditeljId) {
		return listAllZadaciVoditelj(voditeljId).stream().filter(zad -> !zad.isPrivatniZadatak()).toList();
	}

	@Override
	public boolean deleteZadatak(Integer id) {
		zadatakRepo.deleteById(id);
		return true;
	}

	


}
