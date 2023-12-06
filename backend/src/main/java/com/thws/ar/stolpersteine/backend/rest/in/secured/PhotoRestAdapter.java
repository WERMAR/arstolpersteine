package com.thws.ar.stolpersteine.backend.rest.in.secured;

import com.thws.arstolpersteine.gen.api.secured.SecuredPhotosApi;
import com.thws.arstolpersteine.gen.api.secured.model.PhotoUploadDto;
import com.thws.arstolpersteine.gen.api.secured.model.PhotoUploadResponseDto;
import com.thws.arstolpersteine.gen.api.secured.model.ResourcePhotoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PhotoRestAdapter implements SecuredPhotosApi {
    @Override
    public ResponseEntity<ResourcePhotoDto> getPhotosForGroupId(Integer groupID) {
        return null;
    }

    @Override
    public ResponseEntity<PhotoUploadResponseDto> uploadPhotos(List<PhotoUploadDto> photoUploadDto) {
        return null;
    }
}
