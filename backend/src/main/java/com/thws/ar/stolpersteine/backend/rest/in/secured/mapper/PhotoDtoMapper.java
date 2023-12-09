package com.thws.ar.stolpersteine.backend.rest.in.secured.mapper;

import com.thws.ar.stolpersteine.backend.db.entity.Photo;
import com.thws.arstolpersteine.gen.api.secured.model.PhotoUploadResponseDto;
import com.thws.arstolpersteine.gen.api.secured.model.ResourcePhotoDto;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class PhotoDtoMapper {

    public PhotoUploadResponseDto toPhotoUploadResponseDto(Photo photo) {
        return PhotoUploadResponseDto.builder()
                .id(photo.getPhotoId())
                .resourceGroupId(photo.getResourceGroupId())
                .heading(photo.getHeading())
                .downloadURI("/" + photo.getFileUrl())
                .build();
    }

    public ResourcePhotoDto toResourcePhotoDto(Photo photo, Resource resource) {
        return ResourcePhotoDto.builder()
                .id(photo.getPhotoId())
                .photo(resource)
                .build();
    }

}
