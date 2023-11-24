package com.ssafy.trip.qna.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.trip.qna.model.QnaDto;
import org.springframework.security.access.prepost.PreAuthorize;

public interface QnaService {
	Map<String, Object> getQnaList(Map<String, Object> map);
	void setQnaQuestion(Map<String, String> map);
	void updateQnaAnswer(Map<String, String> map);
	QnaDto getQna(int qnaNo);
	void deleteQna(int qnaNo);
	int getListSize(Map<String, Object> map);
}
