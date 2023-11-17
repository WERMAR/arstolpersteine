package entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "Stolperstein")
public class Stolperstein {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int stolperstein_id;

    @Column(name = "description")
    private String description;

    @Column(name = "approved")
    private short approved;

    @OneToMany(mappedBy = "Stolperstein")
    List<User> userList;

    @OneToMany(mappedBy = "Stolperstein")
    List<Victim> victimList;
}
