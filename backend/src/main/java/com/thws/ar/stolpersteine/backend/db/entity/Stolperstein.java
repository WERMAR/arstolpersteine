package com.thws.ar.stolpersteine.backend.db.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "Stolperstein")
public class Stolperstein {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stolperstein_id")
    private Long stolpersteinId;

    @Column(name = "description")
    private String description;

    @Column(name = "lat")
    private float latitude;

    @Column(name = "long")
    private float longitude;

    @Column(name = "active")
    private boolean active;

    @Column(name = "approved_user_name")
    private String approvedUsername;

    @Column(name = "created_user_name")
    private String createdUsername;

    @OneToOne(targetEntity = Address.class)
    @JoinColumn(name = "address_id")
    private Address address;

    @JoinColumn(name = "victim_id")
    @OneToOne(targetEntity = Victim.class)
    private Victim victim;

    @OneToMany(mappedBy = "stolperstein")
    private List<Photo> photos;

}
