package com.thws.ar.stolpersteine.backend.rest.in.secured.mapper;

import com.thws.ar.stolpersteine.backend.db.entity.OverpassStolperstein;
import com.thws.ar.stolpersteine.backend.db.entity.Stolperstein;
import com.thws.arstolpersteine.gen.api.secured.model.StolpersteinPositionDto;
import com.thws.arstolpersteine.gen.api.secured.model.StolpersteineResponseDtoPosition;
import org.springframework.stereotype.Service;

@Service
public class StolpersteinDtoMapper {

    public StolpersteinPositionDto toPositionDto(Stolperstein stolperstein) {
        return StolpersteinPositionDto.builder()
                .id(Long.valueOf(stolperstein.getStolpersteinId()))
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
}
