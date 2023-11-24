package com.ssafy.trip.email.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Getter
@AllArgsConstructor
public enum EmailErrorCode {
    UNABLE_TO_SEND_EMAIL("이메일을 보낼 수 없습니다.", BAD_REQUEST),
    CAN_NOT_MAKE_CODE("인증코드 생성에 실패했습니다.", BAD_REQUEST);

    private final String message;
    private HttpStatus httpStatus;
}
