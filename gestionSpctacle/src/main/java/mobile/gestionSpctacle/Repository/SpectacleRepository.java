package mobile.gestionSpctacle.Repository;

import mobile.gestionSpctacle.Entity.Enum.CategorieSpectacle;
import mobile.gestionSpctacle.Entity.Spectacle;
import mobile.gestionSpectacle.Dto.SpectacleDTO;
import mobile.gestionSpectacle.Dto.TopSpectacleDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import java.util.List;

public interface SpectacleRepository extends JpaRepository<Spectacle, Long> {


    @Query("SELECT DISTINCT s FROM Spectacle s " +
            "JOIN s.seances se " +
            "WHERE se.dateSeance BETWEEN :startDate AND :endDate")
    List<Spectacle> findSpectaclesWithSeancesInNextMonth(@Param("startDate") LocalDate startDate,
                                                         @Param("endDate") LocalDate endDate);
    @Query("SELECT s FROM Spectacle s " +
            "JOIN RecommendedSpectacle rs ON s.id = rs.spectacleId " +
            "ORDER BY rs.id DESC")
    List<Spectacle> findRecommendedSpectacles();
    @Query("SELECT new mobile.gestionSpectacle.Dto.TopSpectacleDTO(s.id, s.titre, s.secondaryImage) " +
            "FROM Spectacle s LEFT JOIN s.seances sc " +
            "GROUP BY s.id ORDER BY COUNT(sc) DESC")
    List<TopSpectacleDTO> findTopSpectaclesBySeanceCount(Pageable pageable);
    @Query("SELECT new mobile.gestionSpectacle.Dto.TopSpectacleDTO(s.id, s.titre, s.secondaryImage) " +
            "FROM Spectacle s " +
            "JOIN s.seances sc " +
            "WHERE sc.dateSeance > CURRENT_DATE " +
            "GROUP BY s.id " +
            "ORDER BY COUNT(sc) DESC")
    List<TopSpectacleDTO> findSpectaclesAfterToday(Pageable pageable);



    @Query("SELECT new mobile.gestionSpectacle.Dto.TopSpectacleDTO(s.id, s.titre, s.secondaryImage) " +
            "FROM Spectacle s LEFT JOIN s.seances sc " +
            "WHERE (:categorie IS NULL OR s.categorie = :categorie) " +
            "GROUP BY s.id ORDER BY COUNT(sc) DESC")
    List<TopSpectacleDTO> findTopSpectaclesBySeanceCountAndCategorie(
            Pageable pageable,
            @Param("categorie") CategorieSpectacle categorie);

    @Query("SELECT new mobile.gestionSpectacle.Dto.TopSpectacleDTO(s.id, s.titre, s.secondaryImage) " +
            "FROM Spectacle s " +
            "JOIN s.seances sc " +
            "WHERE sc.dateSeance > CURRENT_DATE " +
            "AND (:categorie IS NULL OR s.categorie = :categorie) " +
            "GROUP BY s.id " +
            "ORDER BY COUNT(sc) DESC")
    List<TopSpectacleDTO> findSpectaclesAfterTodayByCategorie(
            Pageable pageable,
            @Param("categorie") CategorieSpectacle categorie);

}
