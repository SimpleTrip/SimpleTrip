package com.ssafy.trip.spot.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ssafy.trip.common.Message;

@RestControllerAdvice
public class SpotExceptionHandler {
    @ExceptionHandler(SpotException.class)
    public ResponseEntity<Message> SpotExceptionHandler(SpotException e) {
        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                .body(Message.fail(e.getErrorCode().name(), e.getMessage()));
    }
}
