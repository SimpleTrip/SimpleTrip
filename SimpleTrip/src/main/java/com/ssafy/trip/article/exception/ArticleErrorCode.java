package com.ssafy.trip.article.exception;

import org.springframework.http.HttpStatus;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ArticleErrorCode {
	NON_EXIST("존재하지 않는 글입니다.", BAD_REQUEST);

	private final String message;
	private HttpStatus httpStatus;
}
