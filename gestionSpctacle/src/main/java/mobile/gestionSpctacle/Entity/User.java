package mobile.gestionSpctacle.Entity;

import jakarta.persistence.*;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import mobile.gestionSpctacle.Entity.Enum.Role;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String email;


    @Enumerated(EnumType.STRING)
    private Role role;

}

