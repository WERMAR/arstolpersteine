package com.thws.ar.stolpersteine.backend.rest.in.publicAccess;

import com.thws.ar.stolpersteine.backend.rest.in.publicAccess.mapper.PublicMapperObject;
import com.thws.ar.stolpersteine.backend.service.port.StolpersteinPort;
import com.thws.arstolpersteine.gen.api.publicApi.PublicApi;
import com.thws.arstolpersteine.gen.api.publicApi.model.StolpersteinListResponseDto;
import com.thws.arstolpersteine.gen.api.publicApi.model.StolpersteinPositionDto;
import com.thws.arstolpersteine.gen.api.publicApi.model.StolpersteineResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@RestController
public class PublicRestAdapter implements PublicApi {

    private final StolpersteinPort stolpersteinPort;
    private final PublicMapperObject publicMapperObject;

    @Override
    public ResponseEntity<StolpersteineResponseDto> getStolpersteinForID(Long stolpersteinId) {
        log.info("Called " + stolpersteinId);
        var result = this.stolpersteinPort.getStolpersteinForId(stolpersteinId);
        return ResponseEntity.ok(publicMapperObject.toPublic(result));
    }

    @Override
    public ResponseEntity<List<StolpersteinPositionDto>> getStolpersteineForUser(Double radius, Float latUser, Float lngUser) {
        var responseList = this.stolpersteinPort.getStolpersteinForPositionAndRadius(radius, latUser, lngUser);
        log.info("Found: {}", responseList.size());
        return ResponseEntity.ok(responseList.stream().map(this.publicMapperObject::toPublic).toList());
    }
}
