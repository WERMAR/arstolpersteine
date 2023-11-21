package com.thws.ar.stolpersteine.backend.db.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "Victim")
public class Victim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer victim_id;

    @Column(name = "name")
    private String name;

    @Column(name = "last_name")
    private String last_name;

    @Column(name = "date_of_birth")
    private Date date_of_birth;

    @Column(name = "date_of_death")
    private Date date_of_death;
}
