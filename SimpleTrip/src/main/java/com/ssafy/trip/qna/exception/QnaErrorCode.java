package com.ssafy.trip.qna.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Getter
@AllArgsConstructor
public enum QnaErrorCode {
    NO_QNA("존재하지않는 QnA입니다.", BAD_REQUEST);

    private final String message;
    private HttpStatus httpStatus;
}
