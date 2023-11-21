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

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "User")
    private User uploader;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "Stolperstein")
    private Stolperstein stolperstein;


}
