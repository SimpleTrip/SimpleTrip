package com.ssafy.trip.favorite.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Getter
@AllArgsConstructor
public enum FavoriteErrorCode {
    NON_EXIST("존재하지 않는 즐겨찾기입니다.", BAD_REQUEST);

    private final String message;
    private HttpStatus httpStatus;
}
