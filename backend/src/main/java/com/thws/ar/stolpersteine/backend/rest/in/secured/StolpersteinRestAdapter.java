package com.thws.ar.stolpersteine.backend.rest.in.secured;


import com.thws.arstolpersteine.gen.api.SecuredApi;
import com.thws.arstolpersteine.gen.api.model.StolpersteineReqDto;
import com.thws.arstolpersteine.gen.api.model.StolpersteineResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class StolpersteinRestAdapter implements SecuredApi {

    @Secured({"USER", "ADMIN"})
    @Override
    public ResponseEntity<StolpersteineResponseDto> addStolpersteinForUser(Integer userId, StolpersteineReqDto stolpersteineReqDto) {
        return ResponseEntity.ok(StolpersteineResponseDto.builder().id(232L).build());
    }

    @Secured({"USER", "ADMIN"})
    @Override
    public ResponseEntity<List<StolpersteineResponseDto>> getStolpersteineForUser(Integer userId) {
        log.debug("UserId: {}", userId);
        return ResponseEntity.ok(List.of(StolpersteineResponseDto.builder().id(232L).build()));
    }
}
