package mobile.gestionSpctacle.Repository;

import mobile.gestionSpectacle.Dto.DateCountDTO;

import mobile.gestionSpctacle.Entity.Seance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SeanceRepository extends JpaRepository<Seance, Long> {

    @Query("SELECT s FROM Seance s " +
            "JOIN FETCH s.salle salle " +
            "JOIN FETCH salle.lieu lieu " +
            "JOIN FETCH s.spectacle spectacle " +
            "LEFT JOIN FETCH spectacle.acteurs " +
            "WHERE s.id = :seanceId")
    Seance findSeanceWithDetails(@Param("seanceId") Long seanceId);
    @Query("SELECT s FROM Seance s " +
            "JOIN FETCH s.spectacle spectacle " +
            "JOIN FETCH s.salle salle " +
            "JOIN FETCH salle.lieu lieu " +
            "LEFT JOIN FETCH spectacle.acteurs " +
            "WHERE LOWER(spectacle.titre) LIKE LOWER(CONCAT('%', :titre, '%'))")
    List<Seance> findSeancesBySpectacleTitle(@Param("titre") String titre);

    @Query("SELECT s FROM Seance s " +
            "JOIN FETCH s.spectacle spectacle " +
            "JOIN FETCH s.salle salle " +
            "JOIN FETCH salle.lieu lieu " +
            "LEFT JOIN FETCH spectacle.acteurs " +
            "WHERE s.dateSeance = :date")
    List<Seance> findSeancesByDate(@Param("date") LocalDate date);


    @Query("SELECT new mobile.gestionSpectacle.Dto.DateCountDTO(CAST(s.dateSeance AS string), COUNT(s)) " +
            "FROM Seance s " +
            "GROUP BY s.dateSeance " +
            "ORDER BY s.dateSeance DESC")
    List<DateCountDTO> findDateCounts();
}
