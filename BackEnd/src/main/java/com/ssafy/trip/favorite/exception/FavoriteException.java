package com.ssafy.trip.favorite.exception;

import lombok.Getter;

@Getter
public class FavoriteException extends RuntimeException {
    private final FavoriteErrorCode errorCode;

    public FavoriteException(FavoriteErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
