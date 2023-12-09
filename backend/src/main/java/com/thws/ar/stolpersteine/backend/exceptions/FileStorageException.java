package com.thws.ar.stolpersteine.backend.exceptions;

public class FileStorageException extends RuntimeException {

    public FileStorageException(String message, Exception ex) {
        super(message, ex);
    }
}
