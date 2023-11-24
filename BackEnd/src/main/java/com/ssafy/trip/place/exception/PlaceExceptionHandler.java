package com.ssafy.trip.place.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ssafy.trip.common.Message;

@RestControllerAdvice
public class PlaceExceptionHandler {
    @ExceptionHandler(PlaceException.class)
    public ResponseEntity<Message> PlaceExceptionHandler(PlaceException e) {
        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                .body(Message.fail(e.getErrorCode().name(), e.getMessage()));
    }
}
