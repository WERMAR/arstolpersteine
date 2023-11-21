package com.thws.ar.stolpersteine.backend.db.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;


@Data
@Entity
@Table(name = "User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "name")
    private String name;

    @Column(name = "last_name")
    private String lastname;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role roleId;

    @OneToMany(mappedBy = "createdUser")
    private List<Stolperstein> ownedStolpersteine;

    @OneToMany(mappedBy = "approvedUser")
    private List<Stolperstein> approvedStolpersteine;
}
