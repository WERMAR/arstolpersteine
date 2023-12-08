package com.thws.ar.stolpersteine.backend.service.port;

import com.thws.arstolpersteine.gen.api.secured.model.StolpersteinPositionDto;
import com.thws.arstolpersteine.gen.api.secured.model.StolpersteineResponseDto;
import com.thws.arstolpersteine.gen.api.secured.model.StolpersteineResponseDtoPosition;

import java.util.List;

public interface StolpersteinPort {

    List<StolpersteinPositionDto> getAllStolpersteinPositions();
    StolpersteineResponseDto getStolpersteinForId(Long stolpersteinId);
    int synchroniseOverpassAPI();
}
