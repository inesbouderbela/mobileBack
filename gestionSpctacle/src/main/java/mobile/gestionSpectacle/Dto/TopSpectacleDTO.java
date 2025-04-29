package mobile.gestionSpectacle.Dto;

public class TopSpectacleDTO {

    private Long seanceId;
    private String titre;
    private String secondaryImage;

    // Constructeur
    public TopSpectacleDTO(Long seanceId, String titre, String secondaryImage) {
        this.seanceId = seanceId;
        this.titre = titre;
        this.secondaryImage = secondaryImage;
    }

    // Getters et setters
    public Long getSeanceId() {
        return seanceId;
    }

    public void setSeanceId(Long seanceId) {
        this.seanceId = seanceId;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getSecondaryImage() {
        return secondaryImage;
    }

    public void setSecondaryImage(String secondaryImage) {
        this.secondaryImage = secondaryImage;
    }
}
