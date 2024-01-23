package com.thws.ar.stolpersteine.backend.rest.in.secured.mapper;

import com.thws.ar.stolpersteine.backend.db.entity.Photo;
import com.thws.arstolpersteine.gen.api.secured.model.PhotoResponseDto;
import com.thws.arstolpersteine.gen.api.secured.model.ResourcePhotoDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PhotoDtoMapper {

    public PhotoResponseDto toPhotoUploadResponseDto(Photo photo) {
        return PhotoResponseDto.builder()
                .id(photo.getPhotoId())
                .resourceGroup("/" + photo.getResourceGroupId())
                .heading(photo.getHeading())
                .resourceUrl("/" + photo.getFileUrl())
                .build();
    }

    public ResourcePhotoDto toResourcePhotoDto(List<Photo> photo) {
        return ResourcePhotoDto.builder()
                .photos(photo.stream().map(e -> PhotoResponseDto.builder()
                        .id(e.getPhotoId())
                        .heading(e.getHeading())
                        .resourceGroup("/" + e.getResourceGroupId())
                        .resourceUrl("/" + e.getFileUrl())
                        .build()).toList())
                .build();
    }

}
