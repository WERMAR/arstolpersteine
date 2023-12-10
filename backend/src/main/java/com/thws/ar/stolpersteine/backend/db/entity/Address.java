package com.thws.ar.stolpersteine.backend.db.entity;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Address")
public class Address {

    @Id
    @GeneratedValue(generator = "address_address_id_seq")
    @SequenceGenerator(name="address_address_id_seq",sequenceName="address_address_id_seq", allocationSize=1)
    private Long address_id;

    @Column(name = "street_name")
    private String streetName;

    @Column(name = "house_number")
    private String houseNumber;

    @Column(name = "post_code")
    private String postCode;

    @Column(name = "city")
    private String city;
}
