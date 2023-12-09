package com.thws.ar.stolpersteine.backend.service;


import com.thws.ar.stolpersteine.backend.exceptions.FileStorageException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.Random;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;

    public FileStorageService(@Value("${file.upload.dir}") String filePath) throws FileStorageException {
        this.fileStorageLocation = Paths.get(filePath).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored", ex);
        }
    }

    /**
     * returns the stored filename. Stores uploaded file to a configured path (see <strong>application.properties</strong>)
     *
     * @param file the uploaded file
     * @return the file name
     */
    public String storeFile(MultipartFile file, String resourceGroupId) throws FileStorageException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        Path resourcePath = Path.of(this.fileStorageLocation.toString() + "/" + resourceGroupId).toAbsolutePath().normalize();
        try {
            Files.createDirectories(resourcePath);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored", ex);
        }
        try {
            Path targetLocation = resourcePath.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return targetLocation.toString().substring(targetLocation.toString().indexOf(resourceGroupId));
        } catch (Exception ioe) {
            throw new RuntimeException("Could not store file " + fileName + ". Please check stacktrace", ioe);
        }
    }

    /**
     * Returns a file as a {@link Resource}. Load a file from the disk as a {@link Resource}-Object
     *
     * @param fileName the name of the searched file
     * @return the loaded file as a {@link Resource}
     */
    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new FileStorageException("File not found " + fileName, new Exception());
            }
        } catch (MalformedURLException ex) {
            throw new FileStorageException("File not found " + fileName, ex);
        }
    }

    public boolean removeFile(String fileName) throws Exception {
        Resource resource = this.loadFileAsResource(fileName);
        return resource.getFile().delete();
    }
}
