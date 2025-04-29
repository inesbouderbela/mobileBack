package mobile.gestionSpctacle.Repository;

import mobile.gestionSpctacle.Entity.Seance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SeanceRepository extends JpaRepository<Seance, Long> {

    @Query("SELECT s FROM Seance s " +
            "JOIN FETCH s.salle salle " +
            "JOIN FETCH salle.lieu lieu " +
            "JOIN FETCH s.spectacle spectacle " +
            "LEFT JOIN FETCH spectacle.acteurs " +
            "WHERE s.id = :seanceId")
    Seance findSeanceWithDetails(@Param("seanceId") Long seanceId);
}
