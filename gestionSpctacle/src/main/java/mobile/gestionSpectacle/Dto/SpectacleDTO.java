package mobile.gestionSpectacle.Dto;



public class SpectacleDTO {

    private Long id;
    private String titre;
    private String coverImage;

    public SpectacleDTO(Long id, String titre, String coverImage) {
        this.id = id;
        this.titre = titre;
        this.coverImage = coverImage;
    }

    // Getters et setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }
}
