package entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "Photo")
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int photo_id;

    @Column(name = "lat")
    private float lat;

    @Column(name = "heading")
    private String heading;

    @Column(name = "long")
    private float Long;

    @Column(name = "approved")
    private short approved;

    @OneToMany(mappedBy = "Photo")
    List<User> userList;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Stolperstein")
    private Stolperstein stolperstein;


}
