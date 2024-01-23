package com.thws.ar.stolpersteine.backend.service.port;

import com.thws.arstolpersteine.gen.api.publicApi.model.PhotoDownloadDto;
import com.thws.arstolpersteine.gen.api.secured.model.PhotoResponseDto;
import com.thws.arstolpersteine.gen.api.secured.model.ResourcePhotoDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PhotoPort {

    List<PhotoResponseDto> uploadPhotos(List<MultipartFile> file);
    ResourcePhotoDto getPhotosForGroupId(Long groupId);
    PhotoDownloadDto downloadPub(String fileUri);
    com.thws.arstolpersteine.gen.api.secured.model.PhotoDownloadDto downloadPrivate(String fileUri);
}
