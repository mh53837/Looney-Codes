package hr.fer.progi.looneycodes.BytePit.service.impl;


import hr.fer.progi.looneycodes.BytePit.api.controller.RangDTO;
import hr.fer.progi.looneycodes.BytePit.api.model.Korisnik;
import hr.fer.progi.looneycodes.BytePit.api.model.Natjecanje;
import hr.fer.progi.looneycodes.BytePit.api.model.Pehar;
import hr.fer.progi.looneycodes.BytePit.api.repository.PeharRepository;
import hr.fer.progi.looneycodes.BytePit.service.KorisnikService;
import hr.fer.progi.looneycodes.BytePit.service.NatjecanjeService;
import hr.fer.progi.looneycodes.BytePit.service.PeharService;
import hr.fer.progi.looneycodes.BytePit.service.ZadatakService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.TaskScheduler;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class ScheduledTasks {
    private final TaskScheduler taskScheduler;
    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);
    private ZadatakService zadatakService;
    private NatjecanjeService natjecanjeService;
    private PeharService peharService;
    private PeharRepository peharRepository;
    private KorisnikService korisnikService;

    @Autowired
    public void setKorisnikService(KorisnikService korisnikService) {
        this.korisnikService = korisnikService;
    }
    @Autowired
    public void setZadatakService(ZadatakService zadatakService) {
        this.zadatakService = zadatakService;
    }
    @Autowired
    public void setNatjecanjeService(NatjecanjeService natjecanjeService) {
        this.natjecanjeService = natjecanjeService;
    }
    @Autowired
    public void setPeharRepository(PeharRepository peharRepository) {
        this.peharRepository = peharRepository;
    }
    @Autowired
    public void setPeharService(PeharService peharService) {
        this.peharService = peharService;
    }
    @Autowired
    public ScheduledTasks(TaskScheduler taskScheduler) {
        this.taskScheduler = taskScheduler;
    }

    private void objaviZadatke(Integer natjecanjeId, Timestamp krajNatjecanja) {
        Natjecanje natjecanje = natjecanjeService.getNatjecanje(natjecanjeId);

        if(natjecanje == null || !natjecanje.getKrajNatjecanja().equals(krajNatjecanja))
            return;

        natjecanje.getZadaci().forEach(zadatak -> {
            if (!zadatak.isPrivatniZadatak())
                return;
            zadatak.setPrivatniZadatak(false);
            zadatakService.updateZadatak(zadatak.getZadatakId(), zadatak);
        });

    }

    private void pridruziPehare(Integer natjecanjeId, Timestamp krajNatjecanja) {

        Natjecanje natjecanje = natjecanjeService.getNatjecanje(natjecanjeId);
        if(natjecanje == null || !natjecanje.getKrajNatjecanja().equals(krajNatjecanja))
            return; // natjecanje je updateano, pehari su ili vec dodijeljeni ili ce se dodijeliti kasnije

        List<Pehar> pehari = peharRepository.findAllTrophies().stream().filter(p -> p.getNatjecanje().getNatjecanjeId().equals(natjecanjeId)).toList();
        if(pehari.size() == 0 || pehari.get(0).getNatjecatelj() != null)
            return; // voditelj nije dodao pehar ili su pehari vec dodjeljeni

        Pehar pehar = pehari.get(0);  // mjesto bi mu trebalo biti postavljeno na 1
        List<RangDTO> rangLista = natjecanjeService.getRangList(natjecanjeId);

        for(int i = 0; i < 3; i++){

            //provjerimo da li mozemo indexirati rangListu
            if(i >= rangLista.size()) { // znaci da je broj natjecatelja manji od 3
                break;
            }
            Korisnik korisnik = korisnikService.getKorisnik(rangLista.get(i).getUsername()).get();
            pehar.setNatjecatelj(korisnik);
            pehar.setMjesto(i+1);
            peharRepository.save(pehar); //prvi put updateamo pehar, ostale dodajemo
            pehar.setPeharId(null);
        }

    }

    public void scheduleTaskProblems(Integer natjecanjeId, Timestamp krajNatjecanja) {
        Runnable task = () -> objaviZadatke(natjecanjeId, krajNatjecanja);
        taskScheduler.schedule(task, krajNatjecanja.toInstant());

        if(krajNatjecanja.after(Timestamp.from(Instant.now()))) {
        logger.info("Zadaci natjecanja " + natjecanjeId + " ce biti objavljeni u " + krajNatjecanja);
        }
    }

    public void scheduleTaskTrophy(Integer natjecanjeId, Timestamp krajNatjecanja) {
        Runnable task = () -> pridruziPehare(natjecanjeId, krajNatjecanja);
        taskScheduler.schedule(task, krajNatjecanja.toInstant());

        if(krajNatjecanja.after(Timestamp.from(Instant.now()))) {
            logger.info("Pehari natjecanja " + natjecanjeId + " ce biti dodijeljeni u " + krajNatjecanja);
        }
    }


    @EventListener(ApplicationReadyEvent.class)
    public void scheduleAll() {
        List<Natjecanje> natjecanja = natjecanjeService.listAllNatjecanja();
        // moramo proci kroz sva natjecanja, mozda je koje natjecanje zavrsilo kad je server bio ugasen
        // ako je onda ce se odmah objaviti
        natjecanja.forEach(natjecanje -> {
            scheduleTaskProblems(natjecanje.getNatjecanjeId(), natjecanje.getKrajNatjecanja());
            scheduleTaskTrophy(natjecanje.getNatjecanjeId(), natjecanje.getKrajNatjecanja());
        });

    }

}
