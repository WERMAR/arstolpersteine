package com.thws.ar.stolpersteine.backend.db.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Stolperstein")
public class Stolperstein {

    @Id
    @GeneratedValue(generator="stolperstein_stolperstein_id_seq")
    @SequenceGenerator(name="stolperstein_stolperstein_id_seq",sequenceName="stolperstein_stolperstein_id_seq", allocationSize=1)
    @Column(name = "stolperstein_id")
    private Long stolpersteinId;

    @Column(name = "description")
    private String description;

    @Column(name = "lat")
    private float latitude;

    @Column(name = "lng")
    private float longitude;

    @Column(name = "active")
    private boolean active;

    @Column(name = "approved_user_name")
    private String approvedUsername;

    @Column(name = "created_user_name")
    private String createdUsername;

    @OneToOne(targetEntity = Address.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    @JoinColumn(name = "victim_id")
    @OneToOne(targetEntity = Victim.class, cascade = CascadeType.ALL)
    private Victim victim;

    @OneToMany(mappedBy = "stolperstein")
    private List<Photo> photos;

}
