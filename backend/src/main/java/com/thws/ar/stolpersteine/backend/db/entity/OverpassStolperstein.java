package com.thws.ar.stolpersteine.backend.db.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "overpass_stolperstein")
public class OverpassStolperstein {

    @Id
    @Column(name = "overpass_id")
    private Long id;

    @Column
    private float lat;

    @Column
    private float lng;

    @Column
    private String city;

    @Column
    private String country;

    @Column
    private String housenumber;

    @Column
    private String postcode;

    @Column
    private String street;

    @Column
    private String suburb;

    @Column(name = "memorial_text")
    private String memorialText;

    @Column(name = "victim_name")
    private String victimName;

    @Column(name = "date_of_birth")
    private String dateOfBirth;

    @Column(name = "date_of_death")
    private String dateOfDeath;

}
