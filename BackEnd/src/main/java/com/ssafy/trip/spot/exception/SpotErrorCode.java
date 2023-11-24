package com.ssafy.trip.spot.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SpotErrorCode {
    NON_EXIST("존재하지 않는 장소입니다.", BAD_REQUEST);
	
    private final String message;
    private HttpStatus httpStatus;
}
