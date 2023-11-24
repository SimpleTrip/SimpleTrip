package com.ssafy.trip.user.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UserErrorCode {
    NO_USER("아이디/비밀번호를 확인해주세요.", BAD_REQUEST),
    UNMATCHED_PASS("비밀번호를 확인해주세요.", BAD_REQUEST),
    EXIST_USER("이미 존재하는 ID입니다.", BAD_REQUEST),
    EXIT_USER("탈퇴된 계정입니다.\n새로운 ID를 생성해주세요.", BAD_REQUEST),
    UNMATCHED_ID("일치하지 않는 ID입니다.\n유저 정보를 다시 확인하세요.", BAD_REQUEST),
    UNMATCHED_INFO("일치하지 않는 정보입니다.\n유저 정보를 다시 확인하세요.", BAD_REQUEST),
    NO_TOKEN_IN_COOKIE("토큰이 쿠키에 존재하지 않습니다.", BAD_REQUEST),
    UNMATCHED_CODE("일치하지 않는 코드입니다.", BAD_REQUEST);

    private final String message;
    private HttpStatus httpStatus;
}
