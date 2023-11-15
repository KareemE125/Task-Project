package com.ke.task.exceptions;

import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.time.ZonedDateTime;


@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {EntityNotFoundException.class})
    public ResponseEntity<ApiException> handleEntityNotFoundException(EntityNotFoundException e) {
        ApiException apiException = new ApiException(
                e.getMessage(),
                e,
                HttpStatus.NOT_FOUND.toString(),
                HttpStatus.NOT_FOUND.value(),
                ZonedDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(apiException);
    }

    // Handle Any exception
    @ExceptionHandler()
    public ResponseEntity<ApiException> handleApiRequestException(Exception e) {
        e.printStackTrace();
        ModelMapper modelMapper = new ModelMapper();
        ErrorDTO err = modelMapper.map(e, ErrorDTO.class);

        if (err.getBody() != null) {
            ApiException apiException = new ApiException(
                    err.getBody().getDetail(),
                    e,
                    err.getBody().getTitle(),
                    err.getBody().getStatus(),
                    ZonedDateTime.now()
            );

            return ResponseEntity.status(err.getBody().getStatus()).body(apiException);

        } else {
            ApiException apiException = new ApiException(
                    e.getMessage(),
                    e,
                    HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    ZonedDateTime.now()
            );

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(apiException);

        }
    }
}