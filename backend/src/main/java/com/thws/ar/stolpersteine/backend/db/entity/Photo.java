package com.thws.ar.stolpersteine.backend.db.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Photo")
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long photo_id;

    @Column(name = "heading")
    private Integer heading;

    @Column(name = "approved")
    private short approved;

    @Column(name = "created_user_name")
    private String createdUsername;

    @Column(name = "approved_user_name")
    private String approvedUsername;

    @ManyToOne
    @JoinColumn(name = "stolperstein_id")
    private Stolperstein stolperstein;


}
