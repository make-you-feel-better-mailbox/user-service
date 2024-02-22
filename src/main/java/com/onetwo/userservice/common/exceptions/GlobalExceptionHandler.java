package com.onetwo.userservice.common.exceptions;

import com.onetwo.userservice.common.GlobalStatus;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> badRequestException(BadRequestException e) {
        log.error("BadRequestException", e);
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(BadResponseException.class)
    public ResponseEntity<String> badResponseException(BadResponseException e) {
        log.error("BadResponseException", e);
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(e.getMessage());
    }

    @ExceptionHandler(TokenValidationException.class)
    public ResponseEntity<String> tokenValidationException(TokenValidationException e, HttpServletResponse response) {
        log.debug("TokenValidationException", e);
        response.setHeader(GlobalStatus.TOKEN_VALIDATION_HEADER, e.getJwtCode().getValue());
        return ResponseEntity.badRequest().body(e.getJwtCode().getValue());
    }

    @ExceptionHandler(NotFoundResourceException.class)
    public ResponseEntity<String> notFoundResourceException(NotFoundResourceException e) {
        log.debug("NotFoundResourceException", e);
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.debug("MethodArgumentNotValidException", e);
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> httpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.debug("HttpMessageNotReadableException", e);
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<String> resourceAlreadyExistsException(ResourceAlreadyExistsException e) {
        log.debug("ResourceAlreadyExistsException", e);
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> constraintViolationException(ConstraintViolationException e) {
        log.debug("ConstraintViolationException", e);
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
