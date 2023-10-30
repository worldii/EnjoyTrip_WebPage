package com.ssafy.enjoytrip.global.error;


import java.util.NoSuchElementException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<?> handleNoSuchElementException(NoSuchElementException e) {
        log.error("handleNoSuchElementException", e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EnjoyTripException.class)
    public ResponseEntity<ErrorResponse> handleNoSuchElementException(
            final EnjoyTripException e, final int status) {
        final ErrorResponse errorResponse =
                ErrorResponse.builder().message(e.getMessage()).status(status).build();

        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(status));
    }
}
