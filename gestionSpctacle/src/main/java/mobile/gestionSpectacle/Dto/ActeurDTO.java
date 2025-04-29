package mobile.gestionSpectacle.Dto;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ActeurDTO {
    private Long id;
    private String nom;
    private String prenom;
    private String photo;

}

