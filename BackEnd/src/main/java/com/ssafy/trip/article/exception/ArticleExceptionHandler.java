package com.ssafy.trip.article.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ssafy.trip.common.Message;

@RestControllerAdvice
public class ArticleExceptionHandler {

	@ExceptionHandler(ArticleException.class)
	public ResponseEntity<Message> ArticleExceptionHandler(ArticleException e) {
		return ResponseEntity.status(e.getErrorCode().getHttpStatus())
				.body(Message.fail(e.getErrorCode().name(), e.getMessage()));
	}
}
