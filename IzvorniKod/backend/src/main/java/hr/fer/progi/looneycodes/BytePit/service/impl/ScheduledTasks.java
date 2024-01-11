package hr.fer.progi.looneycodes.BytePit.service.impl;


import hr.fer.progi.looneycodes.BytePit.api.model.Natjecanje;
import hr.fer.progi.looneycodes.BytePit.service.NatjecanjeService;
import hr.fer.progi.looneycodes.BytePit.service.ZadatakService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.TaskScheduler;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Service
public class ScheduledTasks {
    private final TaskScheduler taskScheduler;
    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);
    private ZadatakService zadatakService;
    private NatjecanjeService natjecanjeService;

    @Autowired
    public void setZadatakService(ZadatakService zadatakService) {
        this.zadatakService = zadatakService;
    }
    @Autowired
    public void setNatjecanjeService(NatjecanjeService natjecanjeService) {
        this.natjecanjeService = natjecanjeService;
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

    public void scheduleTask(Integer natjecanjeId, Timestamp krajNatjecanja) {
        Runnable task = () -> objaviZadatke(natjecanjeId, krajNatjecanja);
        taskScheduler.schedule(task, krajNatjecanja.toInstant());

        if(krajNatjecanja.after(Timestamp.from(Instant.now()))) {
        logger.info("Zadaci natjecanja " + natjecanjeId + " ce biti objavljeni u " + krajNatjecanja);
        }
    }
    @EventListener(ApplicationReadyEvent.class)
    public void scheduleAll() {
        List<Natjecanje> natjecanja = natjecanjeService.listAllNatjecanja();
        // moramo proci kroz sva natjecanja, mozda je koje natjecanje zavrsilo kad je server bio ugasen
        // ako je onda ce se odmah objaviti
        natjecanja.forEach(natjecanje -> {
            scheduleTask(natjecanje.getNatjecanjeId(), natjecanje.getKrajNatjecanja());
        });
    }

}
