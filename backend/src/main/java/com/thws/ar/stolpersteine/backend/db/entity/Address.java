package com.thws.ar.stolpersteine.backend.db.entity;

import jakarta.persistence.*;
import lombok.Data;



@Data
@Entity
@Table(name = "Address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer address_id;

    @Column(name = "street_name")
    private String streetName;

    @Column(name = "house_number")
    private Integer houseNumber;

    @Column(name = "post_code")
    private Integer postCode;

    @Column(name = "city")
    private String city;
}
