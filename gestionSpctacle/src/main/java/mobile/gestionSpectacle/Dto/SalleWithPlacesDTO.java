package mobile.gestionSpectacle.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalleWithPlacesDTO {
    private Long id;
    private String nom;
    private int capacite;
    private Long seanceId;
    private List<PlaceDTO> places;
}
