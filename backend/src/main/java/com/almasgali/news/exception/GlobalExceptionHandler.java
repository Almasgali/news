package com.almasgali.news.exception;

import com.almasgali.news.exception.auth.AuthException;
import com.almasgali.news.exception.auth.InvalidTokenException;
import com.almasgali.news.exception.auth.UserAlreadyExistsException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorDetail> catchUserAlreadyExistsException(UserAlreadyExistsException e) {
        log.error(e.getMessage());
        return ErrorDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ErrorDetail> catchAuthException(AuthException e) {
        log.error(e.getMessage());
        return ErrorDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, e.getMessage());
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ErrorDetail> catchInvalidTokenException(InvalidTokenException e) {
        log.error(e.getMessage());
        return ErrorDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, e.getMessage());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorDetail> catchUsernameNotFoundException(UsernameNotFoundException e) {
        log.error(e.getMessage());
        return ErrorDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorDetail> catchBadCredentialsException(BadCredentialsException e) {
        log.error(e.getMessage());
        return ErrorDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, e.getMessage());
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<ErrorDetail> catchMalformedJwtException(MalformedJwtException e) {
        log.error(e.getMessage());
        return ErrorDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(io.jsonwebtoken.security.SecurityException.class)
    public ResponseEntity<ErrorDetail> catchJWTSecurityException(io.jsonwebtoken.security.SecurityException e) {
        log.error(e.getMessage());
        return ErrorDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<ErrorDetail> catchSecurityException(SecurityException e) {
        log.error(e.getMessage());
        return ErrorDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, e.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorDetail> catchMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.error(e.getMessage());
        return ErrorDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorDetail> catchConstraintViolationException(ConstraintViolationException e) {
        log.error(e.getMessage());
        return ErrorDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorDetail> catchRuntimeException(RuntimeException e) {
        log.error(e.getMessage());
        return ErrorDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorDetail> catchHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error(e.getMessage());
        return ErrorDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
    }
}