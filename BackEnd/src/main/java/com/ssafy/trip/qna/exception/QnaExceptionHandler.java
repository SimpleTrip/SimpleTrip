package com.ssafy.trip.qna.exception;

import com.ssafy.trip.common.Message;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class QnaExceptionHandler {
    @ExceptionHandler(QnaException.class)
    public ResponseEntity<Message> QnaExceptionHandler(QnaException e) {
        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                .body(Message.fail(e.getErrorCode().name(), e.getMessage()));
    }
}
