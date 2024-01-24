package com.thws.ar.stolpersteine.backend.db.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Victim")
public class Victim {

    @Id
    @GeneratedValue(generator = "victim_victim_id_seq")
    @Column(name = "victim_id")
    @SequenceGenerator(name="victim_victim_id_seq",sequenceName="victim_victim_id_seq", allocationSize=1)
    private Long victimId;

    @Column(name = "name")
    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Column(name = "date_of_death")
    private Date dateOfDeath;
}
