package mobile.gestionSpctacle.Controller;

import mobile.gestionSpctacle.Repository.SeanceRepository;
import mobile.gestionSpectacle.Dto.DateCountDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController

public class DateController {

    private final SeanceRepository seanceRepository;

    public DateController(SeanceRepository seanceRepository) {
        this.seanceRepository = seanceRepository;
    }

    @GetMapping
    public ResponseEntity<List<DateCountDTO>> getAvailableDatesWithCount() {
        List<DateCountDTO> datesWithCount = seanceRepository.findDateCounts();
        return ResponseEntity.ok(datesWithCount);
    }
}