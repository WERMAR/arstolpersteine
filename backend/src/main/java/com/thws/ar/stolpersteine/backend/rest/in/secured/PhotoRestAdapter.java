package com.thws.ar.stolpersteine.backend.rest.in.secured;

import com.thws.ar.stolpersteine.backend.db.entity.Photo;
import com.thws.ar.stolpersteine.backend.exceptions.FileStorageException;
import com.thws.ar.stolpersteine.backend.service.FileStorageService;
import com.thws.ar.stolpersteine.backend.service.port.PhotoPort;
import com.thws.arstolpersteine.gen.api.secured.SecuredPhotosApi;
import com.thws.arstolpersteine.gen.api.secured.model.PhotoUploadDto;
import com.thws.arstolpersteine.gen.api.secured.model.PhotoUploadResponseDto;
import com.thws.arstolpersteine.gen.api.secured.model.ResourcePhotoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
@RequiredArgsConstructor
public class PhotoRestAdapter implements SecuredPhotosApi {

    private final PhotoPort photoService;

    @Override
    public ResponseEntity<List<ResourcePhotoDto>> getPhotosForGroupId(Long groupID) {
        // TODO: Finish Implementation - currently not working as expected
        return ResponseEntity.ok(this.photoService.getPhotosForGroupId(groupID));
    }

    @Override
    public ResponseEntity<List<PhotoUploadResponseDto>> uploadPhotos(List<MultipartFile> files) {
        List<PhotoUploadResponseDto> response = new ArrayList<>();
        var photoResponse = this.photoService.uploadPhotos(files);
        return ResponseEntity.ok(photoResponse);
    }
}
