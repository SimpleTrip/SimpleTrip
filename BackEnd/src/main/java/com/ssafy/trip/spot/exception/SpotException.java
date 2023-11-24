package com.ssafy.trip.spot.exception;

import lombok.Getter;

@Getter
public class SpotException extends RuntimeException {
    private final SpotErrorCode errorCode;

    public SpotException(SpotErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
