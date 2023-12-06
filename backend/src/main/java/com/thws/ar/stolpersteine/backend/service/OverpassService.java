package com.thws.ar.stolpersteine.backend.service;

import com.thws.ar.stolpersteine.backend.db.entity.mapper.OverpassStolpersteinMapper;
import com.thws.ar.stolpersteine.backend.db.repositories.OverpassStolpersteinRepository;
import com.thws.ar.stolpersteine.backend.rest.out.overpassAPI.OverpassApiRestAdapter;
import com.thws.ar.stolpersteine.backend.rest.out.overpassAPI.model.OverpassElement;
import com.thws.ar.stolpersteine.backend.service.port.OverpassApiPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OverpassService implements OverpassApiPort {

    private final OverpassApiRestAdapter overpassApi;
    private final OverpassStolpersteinRepository overpassStolpersteinRepository;
    private final OverpassStolpersteinMapper overpassStolpersteinMapper;

    @Override
    public int synchronizeStolpersteine() {
        log.info("Start synchronization of Stolpersteine");
        List<OverpassElement> overpassStolpersteine = overpassApi.receiveStolpersteine();
        var overpassEntities = overpassStolpersteine.stream().map(overpassStolpersteinMapper::toEntity).toList();
        overpassStolpersteinRepository.saveAll(overpassEntities);
        return overpassStolpersteine.size();
    }
}
