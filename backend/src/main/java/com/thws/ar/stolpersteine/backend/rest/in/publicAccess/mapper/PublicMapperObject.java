package com.thws.ar.stolpersteine.backend.rest.in.publicAccess.mapper;

import com.thws.arstolpersteine.gen.api.publicApi.model.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublicMapperObject {


    public StolpersteinPositionDto toPublic(com.thws.arstolpersteine.gen.api.secured.model.StolpersteinPositionDto securedDto) {
        return StolpersteinPositionDto.builder()
                .id(securedDto.getId())
                .position(StolpersteinListResponseDtoPosition.builder()
                        .lat(securedDto.getPosition().getLat())
                        .lng(securedDto.getPosition().getLng())
                        .build())
                .build();
    }

    public StolpersteineResponseDto toPublic(com.thws.arstolpersteine.gen.api.secured.model.StolpersteineResponseDto securedDto) {
        return StolpersteineResponseDto.builder()
                .id(securedDto.getId())
                .description(securedDto.getDescription())
                .position(StolpersteinListResponseDtoPosition.builder()
                        .lat(securedDto.getPosition().getLat())
                        .lng(securedDto.getPosition().getLng())
                        .build())
                .address(AddressResponseDto.builder()
                        .city(securedDto.getAddress().getCity())
                        .houseNumber(securedDto.getAddress().getHouseNumber())
                        .postCode(securedDto.getAddress().getPostCode())
                        .streetName(securedDto.getAddress().getStreetName())
                        .build())
                .victim(VictimResponseDto.builder()
                        .firstname(securedDto.getVictim().getFirstname())
                        .lastname(securedDto.getVictim().getLastname())
                        .dateOfBirth(securedDto.getVictim().getDateOfBirth())
                        .dateOfDeath(securedDto.getVictim().getDateOfDeath())
                        .build())
                .photos(securedDto.getPhotos() != null && !securedDto.getPhotos().isEmpty() ? securedDto.getPhotos().stream().map(e -> PhotoResponseDto.builder().heading(e.getHeading()).resourceGroup(e.getResourceGroup()).resourceUrl(e.getResourceUrl()).build()).toList(): List.of())
                .build();
    }
}
