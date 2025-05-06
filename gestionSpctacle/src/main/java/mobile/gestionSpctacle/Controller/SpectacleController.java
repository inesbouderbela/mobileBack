package mobile.gestionSpctacle.Controller;

import mobile.gestionSpctacle.Entity.Enum.CategorieSpectacle;
import mobile.gestionSpctacle.Entity.Seance;
import mobile.gestionSpctacle.Entity.Spectacle;
import mobile.gestionSpctacle.Repository.SeanceRepository;
import mobile.gestionSpctacle.Repository.SpectacleRepository;
import mobile.gestionSpctacle.Service.SpectacleService;
import mobile.gestionSpectacle.Dto.DateCountDTO;
import mobile.gestionSpectacle.Dto.SpectacleDTO;
import mobile.gestionSpectacle.Dto.SpectacleSeanceDetailsDTO;
import mobile.gestionSpectacle.Dto.TopSpectacleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/spectacles")
public class SpectacleController {

    @Autowired
    private SpectacleService spectacleService;
    @Autowired
    private SeanceRepository seanceRepository;
    @Autowired
    private SpectacleRepository spectacleRepository;
    @GetMapping("/next-month")
    public List<SpectacleDTO> getSpectaclesForNextMonth() {

        List<Spectacle> spectacles = spectacleService.getSpectaclesForNextMonth();


        return spectacles.stream()
                .map(s -> new SpectacleDTO(s.getId(), s.getTitre(), s.getCoverImage()))
                .collect(Collectors.toList());
    }
    @GetMapping("/recommended")
    public List<SpectacleDTO> getRecommendedSpectacles() {

        List<Spectacle> spectacles = spectacleService.getRecommendedSpectacles();


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
    @GetMapping("/spectaclesAfterToday/{category}")
    public List<TopSpectacleDTO> getSpectaclesAfterTodayByCategory(
            @PathVariable String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);

        if (category.equalsIgnoreCase("ALL")) {
            return spectacleRepository.findSpectaclesAfterToday(pageable);
        } else {
            CategorieSpectacle categorieEnum = CategorieSpectacle.valueOf(category.toUpperCase());
            return spectacleRepository.findSpectaclesAfterTodayByCategorie(pageable, categorieEnum);
        }
    }

    @GetMapping("/topspectacles/{category}")
    public List<TopSpectacleDTO> getTopSpectaclesByCategory(
            @PathVariable String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);

        if (category.equalsIgnoreCase("ALL")) {
            return spectacleRepository.findTopSpectaclesBySeanceCount(pageable);
        } else {
            CategorieSpectacle categorieEnum = CategorieSpectacle.valueOf(category.toUpperCase());
            return spectacleRepository.findTopSpectaclesBySeanceCountAndCategorie(pageable, categorieEnum);
        }
    }





        @GetMapping("/seance/{seanceId}")
        public ResponseEntity<SpectacleSeanceDetailsDTO> getSpectacleDetailsBySeanceId(@PathVariable Long seanceId) {
            SpectacleSeanceDetailsDTO dto = spectacleService.getSpectacleDetailsBySeanceId(seanceId);
            return ResponseEntity.ok(dto);
        }

    @GetMapping("/searchByName")
    public ResponseEntity<List<SpectacleSeanceDetailsDTO>> searchSeancesBySpectacleTitle(
            @RequestParam String title) {

        List<SpectacleSeanceDetailsDTO> result = spectacleService.getSeancesDetailsBySpectacleTitle(title);

        if (result.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/searchByDate")
    public ResponseEntity<List<SpectacleSeanceDetailsDTO>> getSeancesByDate(
            @RequestParam String date) {

        try {
            // Conversion String -> LocalDate
            LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);

            List<SpectacleSeanceDetailsDTO> result = spectacleService.getSeancesDetailsByDate(localDate);

            if (result.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(result);

        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().body(null);  // Gestion du format invalide
        }
    }
    @GetMapping("/Dates")
    public ResponseEntity<List<DateCountDTO>> getAvailableDatesWithCount() {
        List<DateCountDTO> datesWithCount = seanceRepository.findDateCounts();
        return ResponseEntity.ok(datesWithCount);
    }






}
