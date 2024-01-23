package com.thws.ar.stolpersteine.backend.rest.in.secured;

import com.thws.ar.stolpersteine.backend.service.port.PhotoPort;
import com.thws.arstolpersteine.gen.api.secured.SecuredPhotosApi;
import com.thws.arstolpersteine.gen.api.secured.model.PhotoDownloadDto;
import com.thws.arstolpersteine.gen.api.secured.model.PhotoResponseDto;
import com.thws.arstolpersteine.gen.api.secured.model.ResourcePhotoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PhotoRestAdapter implements SecuredPhotosApi {

    private final PhotoPort photoService;

    @Override
    public ResponseEntity<PhotoDownloadDto> downloadPhoto(Long resourceGroupId, Long resourceId) {
        return ResponseEntity.ok(photoService.downloadPrivate(resourceGroupId + "/" + resourceId));
    }

    @Override
    public ResponseEntity<ResourcePhotoDto> getPhotosForGroupId(Long groupID) {
        return ResponseEntity.ok(this.photoService.getPhotosForGroupId(groupID));
    }

    @Override
    public ResponseEntity<List<PhotoResponseDto>> uploadPhotos(List<MultipartFile> files) {
        var photoResponse = this.photoService.uploadPhotos(files);
        return ResponseEntity.ok(photoResponse);
    }
}
