package com.ssafy.trip.favorite.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ssafy.trip.common.Message;

@RestControllerAdvice
public class FavoriteExceptionHandler {
    @ExceptionHandler(FavoriteException.class)
    public ResponseEntity<Message> FavoriteExceptionHandler(FavoriteException e) {
        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                .body(Message.fail(e.getErrorCode().name(), e.getMessage()));
    }
}
