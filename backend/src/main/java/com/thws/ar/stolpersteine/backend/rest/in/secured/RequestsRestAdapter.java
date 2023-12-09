package com.thws.ar.stolpersteine.backend.rest.in.secured;

import com.thws.arstolpersteine.gen.api.secured.SecuredRequestsApi;
import com.thws.arstolpersteine.gen.api.secured.model.RejectRequestRequest;
import com.thws.arstolpersteine.gen.api.secured.model.RequestResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RequestsRestAdapter implements SecuredRequestsApi {
    @Override
    public ResponseEntity<RequestResponseDto> approveRequest(Integer requestId) {
        return null;
    }

    @Override
    public ResponseEntity<RequestResponseDto> getStolpersteinRequests() {
        return null;
    }

    @Override
    public ResponseEntity<RequestResponseDto> rejectRequest(Integer requestId, RejectRequestRequest rejectRequestRequest) {
        return null;
    }
}
