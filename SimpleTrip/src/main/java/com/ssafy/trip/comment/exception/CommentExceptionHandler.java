package com.ssafy.trip.comment.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ssafy.trip.common.Message;

@RestControllerAdvice
public class CommentExceptionHandler {
    @ExceptionHandler(CommentException.class)
    public ResponseEntity<Message> CommentExceptionHandler(CommentException e) {
        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                .body(Message.fail(e.getErrorCode().name(), e.getMessage()));
    }
}
