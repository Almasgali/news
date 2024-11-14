package com.almasgali.news.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@RequiredArgsConstructor
public class ErrorDetail {
    private final String message;

    public static ResponseEntity<ErrorDetail> forStatusAndDetail(HttpStatus status, String detail) {
        return new ResponseEntity<>(new ErrorDetail(detail), status);
    }
}
