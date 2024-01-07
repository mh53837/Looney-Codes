package hr.fer.progi.looneycodes.BytePit.service.impl;


import hr.fer.progi.looneycodes.BytePit.api.model.Natjecanje;
import hr.fer.progi.looneycodes.BytePit.service.NatjecanjeService;
import hr.fer.progi.looneycodes.BytePit.service.ZadatakService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

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

        if(natjecanje == null)
            throw new IllegalArgumentException("Natjecanje s id-em: " + natjecanjeId + " ne postoji!");

        if(!natjecanje.getKrajNatjecanja().equals(krajNatjecanja))  // dodan je drugi schedule, pa ovaj ignoriramo
            throw new IllegalArgumentException("Timestampovi se ne poklapaju!");

        natjecanje.getZadaci().forEach(zadatak -> {
            zadatak.setPrivatniZadatak(false);
            zadatakService.updateZadatak(zadatak.getZadatakId(), zadatak);
        });
        logger.info("Zadaci natjecanja " + natjecanjeId + " su objavljeni!");

    }

    public void scheduleTask(Integer natjecanjeId, Timestamp krajNatjecanja) {
        Runnable task = () -> objaviZadatke(natjecanjeId, krajNatjecanja);
        taskScheduler.schedule(task, krajNatjecanja.toInstant());
        logger.info("Zadaci natjecanja " + natjecanjeId + " ce biti objavljeni u " + krajNatjecanja);

    }

}
