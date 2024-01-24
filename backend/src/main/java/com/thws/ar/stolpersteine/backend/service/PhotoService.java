package com.thws.ar.stolpersteine.backend.service;

import com.thws.ar.stolpersteine.backend.configuration.security.ClaimInformationService;
import com.thws.ar.stolpersteine.backend.db.entity.Photo;
import com.thws.ar.stolpersteine.backend.db.repositories.PhotoRepository;
import com.thws.ar.stolpersteine.backend.rest.in.secured.mapper.PhotoDtoMapper;
import com.thws.ar.stolpersteine.backend.service.port.PhotoPort;
import com.thws.arstolpersteine.gen.api.publicApi.model.PhotoDownloadDto;
import com.thws.arstolpersteine.gen.api.secured.model.PhotoReqDto;
import com.thws.arstolpersteine.gen.api.secured.model.PhotoResponseDto;
import com.thws.arstolpersteine.gen.api.secured.model.ResourcePhotoDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class PhotoService implements PhotoPort {

    private final FileStorageService fileStorageService;
    private final PhotoRepository photoRepository;
    private final ClaimInformationService claimInformationService;
    private final PhotoDtoMapper photoDtoMapper;

    @Override
    public List<PhotoResponseDto> uploadPhotos(List<MultipartFile> files) {
        String resoureGroupId = String.valueOf(new Random().nextLong(0, Long.MAX_VALUE));
        var photos = new ArrayList<PhotoResponseDto>();
        for (var file : files) {
            var uploadPath = this.fileStorageService.storeFile(file, resoureGroupId);
            var photo = Photo.builder()
                    .approved(false)
                    .heading(-1)
                    .resourceGroupId(Long.valueOf(resoureGroupId))
                    .fileUrl(uploadPath)
                    .createdUsername(claimInformationService.getUsernameFromToken())
                    .build();
            photo = photoRepository.save(photo);
            photos.add(photoDtoMapper.toPhotoUploadResponseDto(photo));
        }
        return photos;
    }

    @Override
    public ResourcePhotoDto getPhotosForGroupId(Long groupId) {
        List<Photo> photos = photoRepository.findByResourceGroupId(groupId);
        return photoDtoMapper.toResourcePhotoDto(photos);
    }

    @Override
    public Resource download(String fileUri) {
        return fileStorageService.loadFileAsResource(fileUri);
    }

    @Override
    public List<Photo> loadPhotosForReq(List<@Valid PhotoReqDto> photos) {
        List<Photo> dbPhotos = new ArrayList<>();
        for (var reqPhoto : photos) {
            var photoOpt = photoRepository.findById(reqPhoto.getId());
            if (photoOpt.isPresent()) {
                var photo = photoOpt.get();
                photo.setHeading(reqPhoto.getHeading());
                dbPhotos.add(photo);
            }
        }
        return dbPhotos;
    }

    @Override
    public void updatePhotos(List<Photo> photos) {
        this.photoRepository.saveAll(photos);
    }
}
