package com.ssafy.trip.plan.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Getter
@AllArgsConstructor
public enum PlanErrorCode {
    NO_PLAN("존재하지않는 플랜입니다.", BAD_REQUEST),
    FAIL_PLAN("플랜등록에 실패했습니다.", BAD_REQUEST);

    private final String message;
    private HttpStatus httpStatus;
}
