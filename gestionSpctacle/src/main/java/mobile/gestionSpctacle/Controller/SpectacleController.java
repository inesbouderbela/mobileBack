package mobile.gestionSpctacle.Controller;

import mobile.gestionSpctacle.Entity.Spectacle;
import mobile.gestionSpctacle.Repository.SpectacleRepository;
import mobile.gestionSpctacle.Service.SpectacleService;
import mobile.gestionSpectacle.Dto.SpectacleDTO;
import mobile.gestionSpectacle.Dto.SpectacleSeanceDetailsDTO;
import mobile.gestionSpectacle.Dto.TopSpectacleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;


import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/spectacles")
public class SpectacleController {



    @Autowired
    private SpectacleService spectacleService;
    @Autowired
    private SpectacleRepository spectacleRepository;
    @GetMapping("/next-month")
    public List<SpectacleDTO> getSpectaclesForNextMonth() {

        List<Spectacle> spectacles = spectacleService.getSpectaclesForNextMonth();


        return spectacles.stream()
                .map(s -> new SpectacleDTO(s.getId(), s.getTitre(), s.getCoverImage()))
                .collect(Collectors.toList());
    }
    @GetMapping("/topspectacles")
    public ResponseEntity<List<TopSpectacleDTO>> getTopSpectacles() {
        try {
            Pageable pageable = PageRequest.of(0, 10);
            List<TopSpectacleDTO> topSpectacles = spectacleRepository.findTopSpectaclesBySeanceCount(pageable);

            if (topSpectacles == null || topSpectacles.isEmpty()) {
                return ResponseEntity.ok(Collections.emptyList());
            }
            return ResponseEntity.ok(topSpectacles);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @GetMapping("/spectaclesAfterToday")
    public ResponseEntity<List<TopSpectacleDTO>> getSpectaclesAfterToday() {
        try {
            Pageable pageable = PageRequest.of(0, 10);
            List<TopSpectacleDTO> spectaclesAfterToday = spectacleRepository.findSpectaclesAfterToday(pageable);

            if (spectaclesAfterToday == null || spectaclesAfterToday.isEmpty()) {
                return ResponseEntity.ok(Collections.emptyList());
            }
            return ResponseEntity.ok(spectaclesAfterToday);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }





        @GetMapping("/seance/{seanceId}")
        public ResponseEntity<SpectacleSeanceDetailsDTO> getSpectacleDetailsBySeanceId(@PathVariable Long seanceId) {
            SpectacleSeanceDetailsDTO dto = spectacleService.getSpectacleDetailsBySeanceId(seanceId);
            return ResponseEntity.ok(dto);
        }




}
