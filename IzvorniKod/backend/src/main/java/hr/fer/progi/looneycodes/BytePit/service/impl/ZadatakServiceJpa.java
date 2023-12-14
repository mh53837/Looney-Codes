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
	public List<Zadatak> listAllJavniZadatak() {
		return zadatakRepo.findAllJavniZadatak();
	}

	@Override
	public Zadatak fetch(Integer id) {
		Optional<Zadatak> zadatak = zadatakRepo.findById(id);
		if(zadatak.isEmpty()) 
			throw new IllegalArgumentException("Zadatak s id-em: " + id + "ne postoji!");
		return zadatak.get();
	}

	@Override
	public Zadatak createZadatak(Zadatak zadatak, String username) {
		Assert.isNull(zadatak.getZadatakId(), "ZadatakId mora biti null prilikom stvaranja zadatka");
		Assert.isNull(zadatak.getVoditelj(), "Voditelj se definira prema korisniku koji je postavio zahtjev!");
		Optional<Korisnik> voditelj = korisnikRepo.findByKorisnickoIme(username);
		if (voditelj.isEmpty())
			throw new IllegalArgumentException("Voditelj < " + username + " > ne postoji!");
		zadatak.setVoditelj(voditelj.get());
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
	public List<Zadatak> findByNatjecateljAllSolved(Korisnik natjecatelj) {
		return zadatakRepo.findByNatjecateljAllSolved(natjecatelj);
	}

	@Override
	public List<Zadatak> listAllZadaciNatjecanje(Natjecanje natjecanje) {
		return zadatakRepo.findByNatjecanje(natjecanje);
	}

	@Override
	public List<Zadatak> listAllZadaciVoditelj(String korisnickoIme) {
		Optional<Korisnik> voditelj = korisnikRepo.findByKorisnickoIme(korisnickoIme);
		if(voditelj.isEmpty()) 
			throw new IllegalArgumentException("Voditelj " + korisnickoIme + " ne postoji!");
		return zadatakRepo.findByVoditelj(voditelj.get());
	}

	@Override
	public List<Zadatak> listAll() {
		return zadatakRepo.findAll();
	}

	@Override
	public List<Zadatak> listAllJavniZadaciVoditelj(String voditeljId) {
		return listAllZadaciVoditelj(voditeljId).stream().filter(zad -> !zad.isPrivatniZadatak()).toList();
	}



}
