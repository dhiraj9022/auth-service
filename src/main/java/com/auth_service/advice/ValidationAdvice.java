package com.auth_service.advice;

import com.auth_service.dto.ResponseDto;
import com.auth_service.exception.NotFoundException;
import com.auth_service.exception.ValidationException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.fasterxml.jackson.databind.exc.ValueInstantiationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ValidationAdvice {

    private static final Logger logger = LoggerFactory.getLogger(ValidationAdvice.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(fieldError ->
                errorResponse.put(fieldError.getField(), fieldError.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(ValidationException exception) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", exception.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, String>> handleAccessDeniedException(AccessDeniedException exception) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", exception.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ResponseDto> notFoundAdvice(NotFoundException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto(exception.getMessage()));
    }

    @ExceptionHandler(UnrecognizedPropertyException.class)
    public ResponseEntity<ResponseDto> handleUnrecognizedPropertyException(UnrecognizedPropertyException ex) {
        String errorMessage = "Unrecognized property: " + ex.getPropertyName();
        return ResponseEntity.badRequest().body(new ResponseDto(errorMessage));
    }

    @ExceptionHandler(ValueInstantiationException.class)
    public ResponseEntity<ResponseDto> handleValueInstantiationException(ValueInstantiationException ex) {
        String errorMessage = "Failed to instantiate class: " + ex.getType().getRawClass().getSimpleName();
        logger.error(errorMessage);
        return ResponseEntity.badRequest().body(new ResponseDto("failed to read req body"));
    }
}