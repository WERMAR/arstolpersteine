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
    private Integer stolperstein_id;

    @Column(name = "description")
    private String description;

    @Column(name = "lat")
    private float latitude;

    @Column(name = "long")
    private float longitude;

    @Column(name = "active")
    private boolean active;

    @ManyToOne
    @JoinColumn(name = "approved_user")
    private User approvedUser;

    @ManyToOne
    @JoinColumn(name = "created_user")
    private User createdUser;

    @OneToOne(targetEntity = Address.class)
    @JoinColumn(name = "address_id")
    private Address address;

    @JoinColumn(name = "victim_id")
    @OneToOne(targetEntity = Victim.class)
    private Victim victim;

    @OneToMany(mappedBy = "stolperstein")
    private List<Photo> photos;

}
