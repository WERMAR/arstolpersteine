package com.thws.ar.stolpersteine.backend.configuration.rest;

import com.thws.ar.stolpersteine.backend.exceptions.FileStorageException;
import com.thws.arstolpersteine.gen.api.secured.model.ProblemDto;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ProblemDto handleUnexcpectedException(Exception ex) {
        log.error("Unexpected Exception", ex);
        return ProblemDto.builder()
                .description(ex.getMessage())
                .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build();
    }

    @ExceptionHandler(NotFoundException.class)
    public ProblemDto handleNotFoundException(NotFoundException nfe) {
        log.warn("Entity/ Property was not found", nfe);
        return ProblemDto.builder()
                .description(nfe.getMessage())
                .errorCode(HttpStatus.NOT_FOUND.value())
                .build();
    }

    @ExceptionHandler(FileStorageException.class)
    public ProblemDto handleFileStorageException(FileStorageException fse) {
        log.warn("Some Errors while storing/ handling files", fse);
        return ProblemDto.builder()
                .description(fse.getMessage())
                .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build();
    }

    @ExceptionHandler(BadRequestException.class)
    public ProblemDto handleFileStorageException(BadRequestException bre) {
        log.warn("Some Errors while storing/ handling files", bre);
        return ProblemDto.builder()
                .description(bre.getMessage())
                .errorCode(HttpStatus.BAD_REQUEST.value())
                .build();
    }
}
