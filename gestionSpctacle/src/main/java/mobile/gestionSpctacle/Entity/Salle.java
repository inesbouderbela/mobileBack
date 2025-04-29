package mobile.gestionSpctacle.Entity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "salles")
public class Salle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private int capacite;
    @ManyToOne
    @JoinColumn(name = "lieu_id")
    private Lieu lieu;
    @OneToMany(mappedBy = "salle", cascade = CascadeType.ALL)
    private List<Place> places;
}

