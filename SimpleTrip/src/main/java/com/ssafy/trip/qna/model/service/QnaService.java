package com.ssafy.trip.qna.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.trip.qna.model.QnaDto;

public interface QnaService {
	List<QnaDto> getQnaList(Map<String, Object> map) throws Exception;
	void setQnaQuestion(Map<String, String> map) throws Exception;
	void updateQnaAnswer(Map<String, String> map) throws Exception;
	QnaDto getQna(int qnaNo) throws Exception;
	void deleteQna(int qnaNo) throws Exception;
	int getListSize() throws Exception;
}
