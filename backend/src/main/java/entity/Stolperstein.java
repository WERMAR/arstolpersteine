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
    private boolean approved;

    @OneToMany(mappedBy = "Stolperstein")
    private List<User> userList;

    @OneToMany(mappedBy = "Stolperstein")
    private List<Victim> victimList;
}
