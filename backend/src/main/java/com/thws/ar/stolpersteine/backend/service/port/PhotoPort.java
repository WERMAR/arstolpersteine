package com.thws.ar.stolpersteine.backend.service.port;

import com.thws.ar.stolpersteine.backend.db.entity.Photo;
import com.thws.arstolpersteine.gen.api.secured.model.PhotoUploadResponseDto;
import com.thws.arstolpersteine.gen.api.secured.model.ResourcePhotoDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PhotoPort {

    List<PhotoUploadResponseDto> uploadPhotos(List<MultipartFile> file);
    List<ResourcePhotoDto> getPhotosForGroupId(Long groupId);
}
