package entity;

import jakarta.persistence.*;
import lombok.Data;



@Data
@Entity
@Table(name = "User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "password")
    private String password;

    @Column(name = "login_email")
    private String email;

    @Column(name = "name")
    private String name;

    @Column(name = "last_name")
    private String lastname;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role roleId;


}
