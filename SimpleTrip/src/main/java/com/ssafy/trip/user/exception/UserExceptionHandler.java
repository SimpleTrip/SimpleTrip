package com.ssafy.trip.user.exception;

import com.ssafy.trip.common.Message;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(UserException.class)
    public ResponseEntity<Message> UserExceptionHandler(UserException e) {
        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                .body(Message.fail(e.getErrorCode().name(), e.getMessage()));
    }
}
