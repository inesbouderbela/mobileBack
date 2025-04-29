package mobile.gestionSpctacle.Repository;

import mobile.gestionSpctacle.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
}