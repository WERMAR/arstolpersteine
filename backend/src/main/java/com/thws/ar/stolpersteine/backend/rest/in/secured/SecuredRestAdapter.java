package com.thws.ar.stolpersteine.backend.rest.in.secured;


import com.thws.ar.stolpersteine.backend.service.OverpassService;
import com.thws.arstolpersteine.gen.api.secured.SecuredApi;
import com.thws.arstolpersteine.gen.api.secured.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class SecuredRestAdapter implements SecuredApi {
    private final OverpassService overpassService;

    @Override
    public ResponseEntity<StolpersteineResponseDto> addStolpersteinForUser(Integer userId, StolpersteineReqDto stolpersteineReqDto) {
        return null;
    }

    @Override
    public ResponseEntity<RequestResponseDto> approveRequest(Integer requestId) {
        return null;
    }

    @Override
    public ResponseEntity<ResourcePhotoDto> getPhotosForGroupId(Integer groupID) {
        return null;
    }

    @Override
    public ResponseEntity<RequestResponseDto> getStolpersteinRequests() {
        return null;
    }

    @Override
    public ResponseEntity<List<StolpersteineResponseDto>> getStolpersteineForUser(Integer userId) {
        return null;
    }

    @Override
    public ResponseEntity<RequestResponseDto> rejectRequest(Integer requestId, RejectRequestRequest rejectRequestRequest) {
        return null;
    }

    @Secured("ADMIN")
    @Override
    public ResponseEntity<SyncOverpassAPI200Response> syncOverpassAPI() {
        log.info("Overpass API Synchronization started");
        var importCounter = this.overpassService.synchronizeStolpersteine();
        return ResponseEntity.ok(SyncOverpassAPI200Response.builder().recordCounter(importCounter).build());
    }

    @Override
    public ResponseEntity<StolpersteineResponseDto> updateStolpersteine(Integer stolpersteinId, StolpersteineReqDto stolpersteineReqDto) {
        return null;
    }

    @Override
    public ResponseEntity<PhotoUploadResponseDto> uploadPhotos(List<PhotoUploadDto> photoUploadDto) {
        return null;
    }
}
