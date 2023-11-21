package com.thws.ar.stolpersteine.backend.db.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Photo")
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer photo_id;

    @Column(name = "heading")
    private Integer heading;

    @Column(name = "approved")
    private short approved;

    @ManyToOne
    @JoinColumn(name = "created_user")
    private User createdUser;

    @ManyToOne
    @JoinColumn(name = "approved_user")
    private User approvedUser;

    @ManyToOne
    @JoinColumn(name = "stolperstein_id")
    private Stolperstein stolperstein;


}
