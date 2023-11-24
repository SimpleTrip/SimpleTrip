package com.ssafy.trip.qna.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.trip.qna.model.QnaDto;

@Mapper
public interface QnaMapper {
	List<QnaDto> getQnaList(Map<String, Object> map);
	void setQnaQuestion(Map<String, String> map);
	void updateQnaAnswer(Map<String, String> map);
	QnaDto getQna(int qnaNo);
	void deleteQna(int qnaNo);
	int getListSize(Map<String, Object> map);
}

