package mobile.gestionSpctacle.Service;

import mobile.gestionSpctacle.Entity.Enum.CategorieSpectacle;
import mobile.gestionSpctacle.Entity.Lieu;
import mobile.gestionSpctacle.Entity.Salle;
import mobile.gestionSpctacle.Entity.Seance;
import mobile.gestionSpctacle.Entity.Spectacle;
import mobile.gestionSpctacle.Repository.SeanceRepository;
import mobile.gestionSpctacle.Repository.SpectacleRepository;
import mobile.gestionSpectacle.Dto.ActeurDTO;
import mobile.gestionSpectacle.Dto.SpectacleSeanceDetailsDTO;
import mobile.gestionSpectacle.Dto.TopSpectacleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SpectacleService {

    @Autowired
    private SpectacleRepository spectacleRepository;
    @Autowired
    private SeanceRepository seanceRepository;


    public List<Spectacle> getSpectaclesForNextMonth() {

        LocalDate startDate = YearMonth.now().plusMonths(1).atDay(1); // Premier jour du mois suivant
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth()); // Dernier jour du mois suivant

        return spectacleRepository.findSpectaclesWithSeancesInNextMonth(startDate, endDate);
    }
    public List<Spectacle> getRecommendedSpectacles() {


        return spectacleRepository.findRecommendedSpectacles();
    }

    public List<SpectacleSeanceDetailsDTO> getSeancesDetailsBySpectacleTitle(String titre) {

        List<Seance> seances = seanceRepository.findSeancesBySpectacleTitle(titre);

        return seances.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }





    public SpectacleSeanceDetailsDTO getSpectacleDetailsBySeanceId(Long seanceId) {
        Seance seance = seanceRepository.findById(seanceId)
                .orElseThrow(() -> new RuntimeException("Séance non trouvée"));

        Spectacle spectacle = seance.getSpectacle();
        Salle salle = seance.getSalle();
        Lieu lieu = salle != null ? salle.getLieu() : null;

        SpectacleSeanceDetailsDTO dto = new SpectacleSeanceDetailsDTO();
        dto.setIdSeances(seanceId);
        dto.setTitle(spectacle.getTitre());
        dto.setIdSpectacle(spectacle.getId());
        dto.setCoverImage(spectacle.getCoverImage());
        dto.setSecondaryImage(spectacle.getSecondaryImage());
        dto.setDescription(spectacle.getDescription());
        dto.setMotsCles(spectacle.getMotsCles());
        dto.setDateSeance(seance.getDateSeance().toString());
        dto.setHeureDebut(seance.getHeureDebut());
        dto.setHeureFin(seance.getHeureFin());
        dto.setPrix(seance.getPrixDeBase());
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
    private SpectacleSeanceDetailsDTO convertToDto(Seance seance) {
        Spectacle spectacle = seance.getSpectacle();
        Salle salle = seance.getSalle();
        Lieu lieu = salle != null ? salle.getLieu() : null;

        SpectacleSeanceDetailsDTO dto = new SpectacleSeanceDetailsDTO();
        dto.setIdSeances(seance.getId());
        dto.setIdSpectacle(spectacle.getId());
        dto.setTitle(spectacle.getTitre());
        dto.setSecondaryImage(spectacle.getSecondaryImage());
        dto.setCoverImage(spectacle.getCoverImage());
        dto.setDescription(spectacle.getDescription());
        dto.setPrix(seance.getPrixDeBase());
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

        List<ActeurDTO> acteurDTOs = spectacle.getActeurs().stream()
                .map(acteur -> {
                    ActeurDTO acteurDTO = new ActeurDTO();
                    acteurDTO.setId(acteur.getId());
                    acteurDTO.setNom(acteur.getNom());
                    acteurDTO.setPrenom(acteur.getPrenom());
                    acteurDTO.setPhoto(acteur.getPhoto());
                    return acteurDTO;
                })
                .collect(Collectors.toList());

        dto.setActeurs(acteurDTOs);

        return dto;
    }
    public List<SpectacleSeanceDetailsDTO> getSeancesDetailsByDate(LocalDate date) {
        List<Seance> seances = seanceRepository.findSeancesByDate(date);

        return seances.stream()
                .map(this::convertToDto)
                .sorted(Comparator.comparing(dto -> LocalTime.parse(dto.getHeureDebut())))
                .collect(Collectors.toList());
    }

}
