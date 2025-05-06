package mobile.gestionSpctacle.Entity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import mobile.gestionSpctacle.Entity.Enum.SeatStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "places")
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int numPlace;
    @Enumerated(EnumType.STRING)
    private SeatStatus statut;

    @ManyToOne
    @JoinColumn(name = "salle_id")
    private Salle salle; }
