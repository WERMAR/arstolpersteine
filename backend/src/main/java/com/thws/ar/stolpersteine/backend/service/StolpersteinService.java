package com.thws.ar.stolpersteine.backend.service;

import com.thws.ar.stolpersteine.backend.configuration.security.ClaimInformationService;
import com.thws.ar.stolpersteine.backend.db.entity.OverpassStolperstein;
import com.thws.ar.stolpersteine.backend.db.entity.Stolperstein;
import com.thws.ar.stolpersteine.backend.db.entity.mapper.StolpersteinMapper;
import com.thws.ar.stolpersteine.backend.db.repositories.OverpassStolpersteinRepository;
import com.thws.ar.stolpersteine.backend.db.repositories.StolpersteinRepository;
import com.thws.ar.stolpersteine.backend.rest.in.secured.mapper.StolpersteinDtoMapper;
import com.thws.ar.stolpersteine.backend.service.port.OverpassApiPort;
import com.thws.ar.stolpersteine.backend.service.port.PhotoPort;
import com.thws.ar.stolpersteine.backend.service.port.StolpersteinPort;
import com.thws.arstolpersteine.gen.api.secured.model.StolpersteinPositionDto;
import com.thws.arstolpersteine.gen.api.secured.model.StolpersteineReqDto;
import com.thws.arstolpersteine.gen.api.secured.model.StolpersteineResponseDto;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@Slf4j
@RequiredArgsConstructor
public class StolpersteinService implements StolpersteinPort {
    private final OverpassApiPort overpassApiPort;
    private final OverpassStolpersteinRepository overpassStolpersteinRepository;
    private final StolpersteinRepository stolpersteinRepository;
    private final PhotoPort photoService;
    private final StolpersteinDtoMapper stolpersteinDtoMapper;
    private final StolpersteinMapper stolpersteinMapper;
    private final ClaimInformationService claimInformationService;

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
    public StolpersteineResponseDto getStolpersteinForId(Long stolpersteinId) {
        var stolpersteinOverpassOpt = this.overpassStolpersteinRepository.findById(stolpersteinId);
        if (stolpersteinOverpassOpt.isPresent()) {
            return stolpersteinDtoMapper.toStolpersteinDto(stolpersteinOverpassOpt.get());
        }
        var stolpersteinOpt = this.stolpersteinRepository.findById(stolpersteinId);
        if (stolpersteinOpt.isPresent()) {
            return stolpersteinDtoMapper.toStolpersteinDto(stolpersteinOpt.get());
        }
        throw new NotFoundException();
    }

    @Override
    public StolpersteineResponseDto updateStolperstein(Long stolpersteinId, StolpersteineReqDto request) {
        var photos = photoService.loadPhotosForReq(request.getPhotos());
        var mappedRequestStolperstein = stolpersteinMapper.toEntity(request);
        mappedRequestStolperstein.setPhotos(photos);
        mappedRequestStolperstein.setCreatedUsername(claimInformationService.getUsernameFromToken());
        Optional<Stolperstein> existingStolpersteinOpt = this.stolpersteinRepository.findById(stolpersteinId);
        if (existingStolpersteinOpt.isEmpty()) {
            Optional<OverpassStolperstein> existingOverpassStolpersteinOpt = this.overpassStolpersteinRepository.findById(stolpersteinId);
            if (existingOverpassStolpersteinOpt.isEmpty()) {
                throw new NotFoundException();
            }
            mappedRequestStolperstein.getPhotos().forEach(e -> e.setStolperstein(mappedRequestStolperstein));
            var updatedStolperstein = this.stolpersteinRepository.save(mappedRequestStolperstein);
            this.overpassStolpersteinRepository.delete(existingOverpassStolpersteinOpt.get());
            return stolpersteinDtoMapper.toStolpersteinDto(updatedStolperstein);
        }
        var existingStolperstein = existingStolpersteinOpt.get();
        this.mergeStolpersteinUpdate(existingStolperstein, mappedRequestStolperstein);
        existingStolperstein.getPhotos().forEach(e -> e.setStolperstein(existingStolperstein));
        var updatedStolperstein = this.stolpersteinRepository.save(existingStolperstein);
        return stolpersteinDtoMapper.toStolpersteinDto(updatedStolperstein);
    }

    @Override
    public int synchroniseOverpassAPI() {
        return overpassApiPort.synchronizeStolpersteine();
    }

    @Override
    public List<StolpersteinPositionDto> getStolpersteinForPositionAndRadius(Double radius, Float latUser, Float lngUser) {
        List<Stolperstein> stolpersteinByPosition = this.stolpersteinRepository.findByCurrentLocationInRadius(lngUser, latUser, radius);
        List<OverpassStolperstein> overpassStolpersteineByPosition = this.overpassStolpersteinRepository.findByCurrentLocationInRadius(lngUser, latUser, radius);
        var overpassListStream = overpassStolpersteineByPosition.stream().map(this.stolpersteinDtoMapper::toPositionDto);
        var stolpersteinListStream = stolpersteinByPosition.stream().map(this.stolpersteinDtoMapper::toPositionDto);
        return Stream.concat(overpassListStream, stolpersteinListStream).toList();
    }


    private void mergeStolpersteinUpdate(Stolperstein existingStolperstein, Stolperstein mappedRequestStolperstein) {
        if (!existingStolperstein.getVictim().getName().equals(mappedRequestStolperstein.getVictim().getName())) {
            existingStolperstein.getVictim().setName(mappedRequestStolperstein.getVictim().getName());
        }
        if (!existingStolperstein.getVictim().getLastName().equals(mappedRequestStolperstein.getVictim().getLastName())) {
            existingStolperstein.getVictim().setName(mappedRequestStolperstein.getVictim().getName());
        }
        if (!existingStolperstein.getVictim().getDateOfBirth().equals(mappedRequestStolperstein.getVictim().getDateOfBirth())) {
            existingStolperstein.getVictim().setDateOfBirth(mappedRequestStolperstein.getVictim().getDateOfBirth());
        }
        if (!existingStolperstein.getVictim().getDateOfDeath().equals(mappedRequestStolperstein.getVictim().getDateOfDeath())) {
            existingStolperstein.getVictim().setDateOfDeath(mappedRequestStolperstein.getVictim().getDateOfDeath());
        }

        if (!existingStolperstein.getAddress().getCity().equals(mappedRequestStolperstein.getAddress().getCity())) {
            existingStolperstein.getAddress().setCity(mappedRequestStolperstein.getAddress().getCity());
        }
        if (!existingStolperstein.getAddress().getHouseNumber().equals(mappedRequestStolperstein.getAddress().getHouseNumber())) {
            existingStolperstein.getAddress().setHouseNumber(mappedRequestStolperstein.getAddress().getHouseNumber());
        }
        if (!existingStolperstein.getAddress().getStreetName().equals(mappedRequestStolperstein.getAddress().getStreetName())) {
            existingStolperstein.getAddress().setStreetName(mappedRequestStolperstein.getAddress().getStreetName());
        }
        if (!existingStolperstein.getAddress().getPostCode().equals(mappedRequestStolperstein.getAddress().getPostCode())) {
            existingStolperstein.getAddress().setPostCode(mappedRequestStolperstein.getAddress().getPostCode());
        }

        if (!existingStolperstein.getDescription().equals(mappedRequestStolperstein.getDescription())) {
            existingStolperstein.setDescription(mappedRequestStolperstein.getDescription());
        }

        existingStolperstein.setPhotos(mappedRequestStolperstein.getPhotos());
    }

}
