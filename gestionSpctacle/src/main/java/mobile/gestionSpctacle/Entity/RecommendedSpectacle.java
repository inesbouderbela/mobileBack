package mobile.gestionSpctacle.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "recommended_spectacles")
public class RecommendedSpectacle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "spectacle_id", unique = true)
    private Long spectacleId;


    public RecommendedSpectacle() {
    }


    public RecommendedSpectacle(Long spectacleId) {
        this.spectacleId = spectacleId;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public Long getSpectacleId() {
        return spectacleId;
    }

    public void setSpectacleId(Long spectacleId) {
        this.spectacleId = spectacleId;
    }
}
