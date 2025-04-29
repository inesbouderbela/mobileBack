package mobile.gestionSpctacle.Service;

import mobile.gestionSpctacle.Entity.Lieu;
import mobile.gestionSpctacle.Entity.Salle;
import mobile.gestionSpctacle.Entity.Seance;
import mobile.gestionSpctacle.Entity.Spectacle;
import mobile.gestionSpctacle.Repository.SeanceRepository;
import mobile.gestionSpctacle.Repository.SpectacleRepository;
import mobile.gestionSpectacle.Dto.ActeurDTO;
import mobile.gestionSpectacle.Dto.SpectacleSeanceDetailsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SpectacleService {

    @Autowired
    private SpectacleRepository spectacleRepository;

    public List<Spectacle> getSpectaclesForNextMonth() {

        LocalDate startDate = YearMonth.now().plusMonths(1).atDay(1); // Premier jour du mois suivant
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth()); // Dernier jour du mois suivant

        return spectacleRepository.findSpectaclesWithSeancesInNextMonth(startDate, endDate);
    }

    @Autowired
    private SeanceRepository seanceRepository;

    public SpectacleSeanceDetailsDTO getSpectacleDetailsBySeanceId(Long seanceId) {
        Seance seance = seanceRepository.findById(seanceId)
                .orElseThrow(() -> new RuntimeException("Séance non trouvée"));

        Spectacle spectacle = seance.getSpectacle();
        Salle salle = seance.getSalle();
        Lieu lieu = salle != null ? salle.getLieu() : null;

        SpectacleSeanceDetailsDTO dto = new SpectacleSeanceDetailsDTO();
        dto.setTitle(spectacle.getTitre());
        dto.setIdSpectacle(spectacle.getId());
        dto.setSecondaryImage(spectacle.getSecondaryImage());
        dto.setDescription(spectacle.getDescription());
        dto.setMotsCles(spectacle.getMotsCles());
        dto.setDateSeance(seance.getDateSeance().toString());
        dto.setHeureDebut(seance.getHeureDebut());
        dto.setHeureFin(seance.getHeureFin());
        dto.setNomSalle(salle != null ? salle.getNom() : null);
        if (lieu != null) {
            dto.setNomLieu(lieu.getNom());
            dto.setAdresseLieu(lieu.getAdresse());
            dto.setLatitude(lieu.getLatitude());
            dto.setLongitude(lieu.getLongitude());
        }

        List<ActeurDTO> acteurDTOs = spectacle.getActeurs().stream().map(acteur -> {
            ActeurDTO acteurDTO = new ActeurDTO();
            acteurDTO.setId(acteur.getId());
            acteurDTO.setNom(acteur.getNom());
            acteurDTO.setPrenom(acteur.getPrenom());
            acteurDTO.setPhoto(acteur.getPhoto());
            return acteurDTO;
        }).collect(Collectors.toList());

        dto.setActeurs(acteurDTOs);

        return dto;
    }
}
