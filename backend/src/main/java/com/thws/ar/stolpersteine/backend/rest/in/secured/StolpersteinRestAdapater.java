package com.thws.ar.stolpersteine.backend.rest.in.secured;


import com.thws.ar.stolpersteine.backend.service.port.StolpersteinPort;
import com.thws.arstolpersteine.gen.api.publicApi.model.StolpersteinListResponseDtoPosition;
import com.thws.arstolpersteine.gen.api.secured.SecuredStolpersteineApi;
import com.thws.arstolpersteine.gen.api.secured.model.StolpersteinPositionDto;
import com.thws.arstolpersteine.gen.api.secured.model.StolpersteineReqDto;
import com.thws.arstolpersteine.gen.api.secured.model.StolpersteineResponseDto;
import com.thws.arstolpersteine.gen.api.secured.model.SyncOverpassAPI200Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class StolpersteinRestAdapater implements SecuredStolpersteineApi {
    private final StolpersteinPort stolpersteinPort;

    @Override
    public ResponseEntity<StolpersteineResponseDto> addStolpersteinForUser(Integer userId, StolpersteineReqDto stolpersteineReqDto) {
        return null;
    }

    @Secured({"USER", "ADMIN"})
    @Override
    public ResponseEntity<List<StolpersteinPositionDto>> getAllStolpersteine() {
        log.info("Get All Stolpersteine currently in the System available");
        var result = stolpersteinPort.getAllStolpersteinPositions();
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<List<StolpersteineResponseDto>> getStolpersteineForUser(Integer userId) {
        return null;
    }

    @Secured("ADMIN")
    @Override
    public ResponseEntity<SyncOverpassAPI200Response> syncOverpassAPI() {
        log.info("Overpass API Synchronization started");
        var importCounter = this.stolpersteinPort.synchroniseOverpassAPI();
        return ResponseEntity.ok(SyncOverpassAPI200Response.builder().recordCounter(importCounter).build());
    }

    @Override
    public ResponseEntity<StolpersteineResponseDto> updateStolpersteine(Integer stolpersteinId, StolpersteineReqDto stolpersteineReqDto) {
        return null;
    }
}
