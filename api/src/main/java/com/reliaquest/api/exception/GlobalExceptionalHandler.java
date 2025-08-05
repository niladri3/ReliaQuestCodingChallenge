package com.reliaquest.api.exception;

import com.reliaquest.api.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.ResourceAccessException;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionalHandler {

    @ExceptionHandler(ClientException.class)
    public ResponseEntity<ErrorResponse> handleClientException(ClientException ce) {
        log.error("ClientException error message : {}", ce.getMessage(), ce);
        ErrorResponse errorResponse = ErrorResponse.builder()
                .details(ce.getMessage())
                .timestamp(LocalDate.now())
                .build();
        return new ResponseEntity<>(errorResponse, ce.getStatusCode());
    }

    @ExceptionHandler(ServerException.class)
    public ResponseEntity<ErrorResponse> handleServerException(ServerException se) {
        log.error("ServerException error message : {}", se.getMessage(), se);
        ErrorResponse errorResponse = ErrorResponse.builder()
                .details(se.getMessage())
                .timestamp(LocalDate.now())
                .build();
        return new ResponseEntity<>(errorResponse, se.getStatusCode());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException me) {
        Map<String, String> errors = new HashMap<>();
        me.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        log.error("Validation Failure. Error message : {}", errors, me);
        ErrorResponse errorResponse = ErrorResponse.builder()
                .details("Validation Failure. Error message : " + errors)
                .timestamp(LocalDate.now())
                .build();
        return new ResponseEntity<>(errorResponse, me.getStatusCode());
    }

    @ExceptionHandler(ResourceAccessException.class)
    public ResponseEntity<ErrorResponse> handleResourceAccessException(ResourceAccessException se) {
        log.error("Employee Server is not responding. Error message : {}", se.getMessage(), se);
        ErrorResponse errorResponse = ErrorResponse.builder()
                .details("Employee Server is not responding. Please try again after some time.")
                .timestamp(LocalDate.now())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error("The system encountered an unexpected issue.Error : {}", e.getMessage(), e);
        ErrorResponse errorResponse = ErrorResponse.builder()
                .details("The system encountered an unexpected issue.")
                .timestamp(LocalDate.now())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
