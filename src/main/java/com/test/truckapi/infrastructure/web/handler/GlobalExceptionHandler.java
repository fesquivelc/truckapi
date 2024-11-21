package com.test.truckapi.infrastructure.web.handler;

import com.test.truckapi.infrastructure.web.dto.ErrorResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponseDTO> handleResponseStatusException(ResponseStatusException ex) {
        var errorResponse = ErrorResponseDTO.builder()
                .message(ex.getReason())
                .status(ex.getStatusCode().value())
                .build();
        return new ResponseEntity<>(errorResponse, ex.getStatusCode());
    }
}
