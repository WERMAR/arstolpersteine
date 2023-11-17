package entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "Address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int address_id;

    @Column(name = "street_name")
    private String streetName;

    @Column(name = "house_number")
    private int houseNumber;

    @Column(name = "post_code")
    private int postCode;

    @Column(name = "suburb")
    private String suburb;

    @Column(name = "long")
    private float Long;

    @Column(name = "lat")
    private float lat;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Stolperstein")
    private Stolperstein stolperstein;
}
