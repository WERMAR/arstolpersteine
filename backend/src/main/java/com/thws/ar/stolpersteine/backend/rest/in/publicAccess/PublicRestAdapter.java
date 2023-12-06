package com.thws.ar.stolpersteine.backend.rest.in.publicAccess;

import com.thws.arstolpersteine.gen.api.publicApi.PublicApi;
import com.thws.arstolpersteine.gen.api.publicApi.model.StolpersteinListResponseDto;
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

    @Override
    public ResponseEntity<StolpersteineResponseDto> getStolpersteinForID(Integer stolpersteinId) {
        return null;
    }

    @Override
    public ResponseEntity<List<StolpersteinListResponseDto>> getStolpersteineForUser(Integer radius, Float latUser, Float lngUser) {
        return null;
    }
}
