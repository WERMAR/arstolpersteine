package com.thws.ar.stolpersteine.backend.service;

import com.thws.ar.stolpersteine.backend.db.repositories.OverpassStolpersteinRepository;
import com.thws.ar.stolpersteine.backend.db.repositories.StolpersteinRepository;
import com.thws.ar.stolpersteine.backend.rest.in.secured.mapper.StolpersteinDtoMapper;
import com.thws.ar.stolpersteine.backend.service.port.OverpassApiPort;
import com.thws.ar.stolpersteine.backend.service.port.StolpersteinPort;
import com.thws.arstolpersteine.gen.api.secured.model.StolpersteinPositionDto;
import com.thws.arstolpersteine.gen.api.secured.model.StolpersteineResponseDto;
import com.thws.arstolpersteine.gen.api.secured.model.StolpersteineResponseDtoPosition;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
@Slf4j
@RequiredArgsConstructor
public class StolpersteinService implements StolpersteinPort {
    private final OverpassApiPort overpassApiPort;
    private final OverpassStolpersteinRepository overpassStolpersteinRepository;
    private final StolpersteinRepository stolpersteinRepository;
    private final StolpersteinDtoMapper stolpersteinDtoMapper;

    @Override
    public List<StolpersteinPositionDto> getAllStolpersteinPositions() {
        var overpassStolpersteine = overpassStolpersteinRepository.findAll();
        var stolpersteine = stolpersteinRepository.findAll();
        log.debug("OverpassStolpersteine Amount: {} | Stolpersteine Amount: {}", overpassStolpersteine.size(), stolpersteine.size());
        var mappedPositionListOverpassStream = overpassStolpersteine.stream().map(stolpersteinDtoMapper::toPositionDto);
        var mappedPositionListStolpersteineStream = stolpersteine.stream().map(stolpersteinDtoMapper::toPositionDto);
        return Stream.concat(mappedPositionListStolpersteineStream, mappedPositionListOverpassStream).toList();
    }

    @Override
    public int synchroniseOverpassAPI() {
        return overpassApiPort.synchronizeStolpersteine();
    }
}
