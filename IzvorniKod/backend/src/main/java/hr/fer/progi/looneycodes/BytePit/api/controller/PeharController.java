package hr.fer.progi.looneycodes.BytePit.api.controller;

import hr.fer.progi.looneycodes.BytePit.api.model.Natjecanje;
import hr.fer.progi.looneycodes.BytePit.api.model.Pehar;
import hr.fer.progi.looneycodes.BytePit.api.model.Zadatak;
import hr.fer.progi.looneycodes.BytePit.service.NatjecanjeService;
import hr.fer.progi.looneycodes.BytePit.service.PeharService;
import hr.fer.progi.looneycodes.BytePit.service.RequestDeniedException;
import hr.fer.progi.looneycodes.BytePit.service.impl.ScheduledTasks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/**
 *
 * Kontroler koji služi pristupu metodama vezanim uz Pehar entitet.
 * Ovdje su definirane adrese preko kojih mozemo kroz browser/postman pristupati našim servisima.
 */

@RestController
@RequestMapping("/api/trophies")
public class PeharController {

    @Autowired
    private PeharService peharService;
    @Autowired
    private ScheduledTasks scheduledTasks;

    /**
     * Ruta za ispis svih pehara.
     * @return lista svih pehara.
     */
    @GetMapping("/all")
    public List<AddPeharDTO> listAll() {
        return peharService.listAll().stream().map(AddPeharDTO::new).toList();
    }

    /**
     * Ruta za ispis jednog pehara.
     * @return jedan pehar.
     */
    @GetMapping("/get/{id}")
    public AddPeharDTO oneTrophy(@PathVariable Integer id) {
        return new AddPeharDTO(peharService.oneTrophy(id));
    }

    /**
     * Ruta za ispis svih pehara jednog korisnika.
     * @return lista pehara.
     */
    @GetMapping("/user/{korisnickoIme}")
    public List<AddPeharDTO> listAllFromOneKorisnik(@PathVariable String korisnickoIme){
        return peharService.listAllFromOneNatjecatelj(korisnickoIme).stream().map(AddPeharDTO::new).toList();
    }

    /**
     * Dodaj novi pehar.
     *
     */
    @PostMapping("/add")
    public AddPeharDTO addPehar(@RequestPart("image") MultipartFile file, @RequestPart("peharData")  AddPeharDTO dto){
        try {
            int extensionIndex = file.getOriginalFilename().lastIndexOf('.');
            if (extensionIndex < 1) {
                dto.setSlikaPehara(null);
            } else {
                String extension = file.getOriginalFilename().substring(extensionIndex);

                if (!List.of(".jpg", ".jpeg", ".png").contains(extension))
                    throw new RequestDeniedException("Format slike nije podržan! Podržani formati su .jpg, .jpeg, .png");

               Path savePath = Path.of("./src/main/resources/slikePehara").resolve(dto.getNatjecanjeId() +  extension);
               file.transferTo(savePath);
               dto.setSlikaPehara(savePath.toString());
            }
        } catch (IOException e) {
            throw new RequestDeniedException("Nije uspio upload slike");
        }

        Pehar pehar = peharService.createPehar(dto);
        scheduledTasks.scheduleTaskTrophy(pehar.getNatjecanje().getNatjecanjeId(), pehar.getNatjecanje().getKrajNatjecanja());
        return new AddPeharDTO(pehar);
    }
    /**
     * Dohvati sliku pehara
     * @param peharId id pehara za kojeg trazimo sliku
     * @return slika pehara
     */
    @GetMapping("/image/{peharId}")
    public ResponseEntity<byte[]> getProfilePicture(@PathVariable("peharId") Integer peharId) {
        Pair<byte[], MediaType> image = peharService.getImage(peharId);
        return ResponseEntity.ok()
                .contentType(image.getSecond())
                .body(image.getFirst());
    }
}
