package com.interviewmate.userservice.aspect;

import java.time.Instant;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.interviewmate.userservice.dto.ApiErrorResponse;
import com.interviewmate.userservice.exception.BaseException;

import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.AuthenticationException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiErrorResponse> handleValidation(
      MethodArgumentNotValidException ex) {

    String message = ex.getBindingResult()
        .getFieldErrors()
        .stream()
        .findFirst()
        .map(e -> e.getField() + " " + e.getDefaultMessage())
        .orElse("Validation failed");

    return buildResponse(
        message,
        "VALIDATION_ERROR",
        HttpStatus.BAD_REQUEST);
  }

    // Handle all business exceptions
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ApiErrorResponse> handleBaseException(BaseException ex) {

        log.warn("Business exception: {}", ex.getMessage());

        return buildResponse(
                ex.getMessage(),
                ex.getErrorCode(),
                ex.getStatus()
        );
    }

    // Handle Spring Security authentication errors
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiErrorResponse> handleAuthenticationException(AuthenticationException ex) {

        log.warn("Authentication failed: {}", ex.getMessage());

        return buildResponse(
                "Invalid email or password",
                "AUTHENTICATION_FAILED",
                HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(RedisConnectionFailureException.class)
    public ResponseEntity<ApiErrorResponse> handleRedisException(RedisConnectionFailureException ex) {

        log.error("Redis error occurred", ex);

        return buildResponse(
                "Cache service unavailable",
                "CACHE_UNAVAILABLE",
                HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ApiErrorResponse> handleDatabaseException(DataAccessException ex) {

        log.error("Database error occurred", ex);

        return buildResponse(
                "Database temporarily unavailable",
                "DATABASE_ERROR",
                HttpStatus.SERVICE_UNAVAILABLE);
    }


    // Catch ALL unknown exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGeneral(Exception ex) {

        log.error("Unhandled exception occurred", ex);

        return buildResponse(
                "Something went wrong. Please try again later.",
                "INTERNAL_SERVER_ERROR",
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    

    private ResponseEntity<ApiErrorResponse> buildResponse(
            String message,
            String errorCode,
            HttpStatus status) {

        ApiErrorResponse response = ApiErrorResponse.builder()
                .success(false)
                .message(message)
                .errorCode(errorCode)
                .timestamp(Instant.now())
                .build();

        return new ResponseEntity<>(response, status);
    }


}
