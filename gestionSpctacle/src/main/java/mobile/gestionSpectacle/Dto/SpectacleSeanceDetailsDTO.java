package mobile.gestionSpectacle.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class SpectacleSeanceDetailsDTO {
    private Long idSpectacle;
    private String title;
    private String secondaryImage;
    private String coverImage;
    private String description;
    private String motsCles;
    private String dateSeance;
    private String heureDebut;
    private String heureFin;
    private String nomSalle;
    private String nomLieu;
    private String adresseLieu;
    private Double latitude;
    private Double longitude;
    private List<ActeurDTO> acteurs;
}