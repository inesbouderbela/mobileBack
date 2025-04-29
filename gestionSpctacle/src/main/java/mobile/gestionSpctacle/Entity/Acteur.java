package mobile.gestionSpctacle.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class Acteur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String prenom;
    private String photo; // chemin ou URL de la photo

    @ManyToMany(mappedBy = "acteurs")
    private List<Spectacle> spectacles = new ArrayList<>();
}

