package com.ssafy.trip.plan.exception;

import com.ssafy.trip.common.Message;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PlanExceptionHandler {
    @ExceptionHandler(PlanException.class)
    public ResponseEntity<Message> PlanExceptionHandler(PlanException e) {
        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                .body(Message.fail(e.getErrorCode().name(), e.getMessage()));
    }
}
