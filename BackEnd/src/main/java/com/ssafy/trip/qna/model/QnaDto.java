package com.ssafy.trip.qna.model;

import lombok.Data;

@Data
public class QnaDto {
	private int qnaNo;
	private String question;
	private String answer;
	private String questionUserId;
	private String answerAdminId;
}
