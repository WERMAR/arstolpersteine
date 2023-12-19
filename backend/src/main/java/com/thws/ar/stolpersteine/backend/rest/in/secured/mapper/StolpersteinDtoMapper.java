package com.thws.ar.stolpersteine.backend.rest.in.secured.mapper;

import com.thws.ar.stolpersteine.backend.db.entity.OverpassStolperstein;
import com.thws.ar.stolpersteine.backend.db.entity.Stolperstein;
import com.thws.arstolpersteine.gen.api.secured.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

@Service
@Slf4j
public class StolpersteinDtoMapper {

    public StolpersteinPositionDto toPositionDto(Stolperstein stolperstein) {
        return StolpersteinPositionDto.builder()
                .id(stolperstein.getStolpersteinId())
                .position(StolpersteineResponseDtoPosition.builder()
                        .lat(stolperstein.getLatitude())
                        .lng(stolperstein.getLongitude())
                        .build())
                .build();
    }

    public StolpersteinPositionDto toPositionDto(OverpassStolperstein overpassStolperstein) {
        return StolpersteinPositionDto.builder()
                .id(overpassStolperstein.getId())
                .position(StolpersteineResponseDtoPosition.builder()
                        .lat(overpassStolperstein.getLat())
                        .lng(overpassStolperstein.getLng())
                        .build())
                .build();
    }

    public StolpersteineResponseDto toStolpersteinDto(OverpassStolperstein overpassStolperstein) {
        StolpersteineResponseDto stolpersteineResponseDto = StolpersteineResponseDto.builder()
                .id(overpassStolperstein.getId())
                .description(overpassStolperstein.getMemorialText())
                .position(StolpersteineResponseDtoPosition.builder()
                        .lng(overpassStolperstein.getLng())
                        .lat(overpassStolperstein.getLat())
                        .build())
                .address(AddressResponseDto.builder()
                        .id(new Random().nextLong())
                        .city(overpassStolperstein.getCity())
                        .houseNumber(overpassStolperstein.getHousenumber())
                        .postCode(overpassStolperstein.getPostcode())
                        .streetName(overpassStolperstein.getStreet())
                        .build())
                .victim(VictimResponseDto.builder()
                        .firstname(getFirstNameOfOverpassStolperstein(overpassStolperstein.getVictimName()))
                        .lastname(getLastNameOfOverpassStolperstein(overpassStolperstein.getVictimName()))
                        .build())
                .build();
        try {
            stolpersteineResponseDto.getVictim().setDateOfBirth(LocalDate.parse(overpassStolperstein.getDateOfBirth(), DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        } catch (Exception e) {
            log.debug("Date of Birth is not parseable is skipped in the return object");
        }
        try {
            stolpersteineResponseDto.getVictim().setDateOfDeath(LocalDate.parse(overpassStolperstein.getDateOfDeath(), DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        } catch (Exception e) {
            log.debug("Date of Death is not parseable is skipped in the return object");
        }
        return stolpersteineResponseDto;
    }

    private String getFirstNameOfOverpassStolperstein(String victimName) {
        return victimName.substring(0, victimName.indexOf(' '));
    }

    private String getLastNameOfOverpassStolperstein(String victimName) {
        return victimName.substring(victimName.indexOf(' ') + 1);
    }

    public StolpersteineResponseDto toStolpersteinDto(Stolperstein stolperstein) {
        return StolpersteineResponseDto.builder()
                .id(stolperstein.getStolpersteinId())
                .description(stolperstein.getDescription())
                .position(StolpersteineResponseDtoPosition.builder()
                        .lng(stolperstein.getLongitude())
                        .lat(stolperstein.getLatitude())
                        .build())
                .address(AddressResponseDto.builder()
                        .id(stolperstein.getAddress().getAddress_id())
                        .city(stolperstein.getAddress().getCity())
                        .houseNumber(String.valueOf(stolperstein.getAddress().getHouseNumber()))
                        .postCode(String.valueOf(stolperstein.getAddress().getPostCode()))
                        .streetName(stolperstein.getAddress().getStreetName())
                        .build())
                .victim(VictimResponseDto.builder()
                        .dateOfBirth(((Date) stolperstein.getVictim().getDateOfBirth()).toLocalDate())
                        .dateOfDeath(((Date) stolperstein.getVictim().getDateOfBirth()).toLocalDate())
                        .firstname(stolperstein.getVictim().getName())
                        .lastname(stolperstein.getVictim().getLastName())
                        .build())
                .photos(List.of())
                .build();
    }
}
