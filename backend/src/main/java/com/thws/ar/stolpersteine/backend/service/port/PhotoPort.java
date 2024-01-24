package com.thws.ar.stolpersteine.backend.service.port;

import com.thws.ar.stolpersteine.backend.db.entity.Photo;
import com.thws.arstolpersteine.gen.api.secured.model.PhotoReqDto;
import com.thws.arstolpersteine.gen.api.secured.model.PhotoResponseDto;
import com.thws.arstolpersteine.gen.api.secured.model.ResourcePhotoDto;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PhotoPort {

    List<PhotoResponseDto> uploadPhotos(List<MultipartFile> file);
    ResourcePhotoDto getPhotosForGroupId(Long groupId);
    Resource download(String fileUri);
    List<Photo> loadPhotosForReq(List<PhotoReqDto> photos);
    void updatePhotos(List<Photo> photos);
}
