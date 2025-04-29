package mobile.gestionSpctacle.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "spectacles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Spectacle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;
    private String description;

    private String coverImage;
    private String secondaryImage;

    @OneToMany(mappedBy = "spectacle", cascade = CascadeType.ALL)
    private List<Seance> seances = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "spectacle_acteur",
            joinColumns = @JoinColumn(name = "spectacle_id"),
            inverseJoinColumns = @JoinColumn(name = "acteur_id")
    )
    private List<Acteur> acteurs = new ArrayList<>();
    private String motsCles;

}

