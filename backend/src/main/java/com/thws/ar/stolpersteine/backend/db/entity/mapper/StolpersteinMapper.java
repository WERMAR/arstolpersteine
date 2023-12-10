package com.thws.ar.stolpersteine.backend.db.entity.mapper;

import com.thws.ar.stolpersteine.backend.db.entity.Address;
import com.thws.ar.stolpersteine.backend.db.entity.Stolperstein;
import com.thws.ar.stolpersteine.backend.db.entity.Victim;
import com.thws.arstolpersteine.gen.api.secured.model.StolpersteineReqDto;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Component
public class StolpersteinMapper {

    public Stolperstein toEntity(StolpersteineReqDto requestDto) {
        return Stolperstein.builder()
                .latitude(requestDto.getPosition().getLat())
                .longitude(requestDto.getPosition().getLng())
                .description(requestDto.getDescription())
                .victim(Victim.builder()
                        .name(requestDto.getVictim().getFirstname())
                        .lastName(requestDto.getVictim().getLastname())
                        .dateOfDeath(Date.valueOf(requestDto.getVictim().getDateOfBirth()))
                        .dateOfBirth(Date.valueOf(requestDto.getVictim().getDateOfDeath()))
                        .build())
                .address(Address.builder()
                        .city(requestDto.getAddress().getCity())
                        .streetName(requestDto.getAddress().getStreetName())
                        .houseNumber(requestDto.getAddress().getHouseNumber())
                        .postCode(requestDto.getAddress().getPostCode())
                        .build())
                .active(false)
                .build();
    }
}
