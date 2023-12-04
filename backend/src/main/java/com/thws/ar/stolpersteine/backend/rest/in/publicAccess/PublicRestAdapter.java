package com.thws.ar.stolpersteine.backend.rest.in.publicAccess;

import com.thws.arstolpersteine.gen.api.publicApi.PublicApi;
import com.thws.arstolpersteine.gen.api.publicApi.model.StolpersteineResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PublicRestAdapter implements PublicApi {

    @Override
    public ResponseEntity<List<StolpersteineResponseDto>> getStolpersteineForUser(Integer radius, Float latUser, Float lngUser) {
        return null;
    }
}
