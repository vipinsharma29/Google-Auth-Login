package com.vipin.googleAuthLogin.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleSecurityException(Exception exception) {

        if (exception instanceof IllegalArgumentException) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }

        if (exception instanceof NoSuchElementException) {
            return new ResponseEntity<>("The resource was not found", HttpStatus.NOT_FOUND);
        }

        if (exception instanceof BadRequestException) {
            return new ResponseEntity<>("The request is invalid", HttpStatus.BAD_REQUEST);
        }

        if (exception instanceof MethodArgumentNotValidException methodArgumentNotValidException) {
            Map<String, String> errorMap = new HashMap<>();
            methodArgumentNotValidException.getBindingResult().getFieldErrors().forEach(error -> errorMap.put(error.getField(), error.getDefaultMessage()));
            return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
        }

        if (exception instanceof BadCredentialsException) {
            return new ResponseEntity<>("Wrong Username or password", HttpStatus.UNAUTHORIZED);
        }

        if (exception instanceof AccountStatusException) {
            return new ResponseEntity<>("The account is block", HttpStatus.FORBIDDEN);
        }

        if (exception instanceof AccessDeniedException) {
            return new ResponseEntity<>("UnAuthorized", HttpStatus.FORBIDDEN);
        }

        if (exception instanceof SignatureException) {
            return new ResponseEntity<>("Invalid Token", HttpStatus.FORBIDDEN);
        }

        if (exception instanceof ExpiredJwtException) {
            return new ResponseEntity<>("Token expired", HttpStatus.FORBIDDEN);
        }

        if (exception instanceof HttpRequestMethodNotSupportedException) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>("Internal server error.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
