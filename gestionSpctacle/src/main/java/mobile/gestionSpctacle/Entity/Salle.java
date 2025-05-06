package mobile.gestionSpctacle.Entity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import mobile.gestionSpctacle.Entity.Enum.SeatStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    @OneToMany(mappedBy = "salle", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Place> places;

    public void initPlaces() {

            places = new ArrayList<>();


        for (int i = 1; i <= capacite; i++) {
            Place place = new Place();
            place.setNumPlace(i);
            place.setSalle(this);
            place.setStatut(SeatStatus.AVAILABLE);
            places.add(place);
        }
    }


}

