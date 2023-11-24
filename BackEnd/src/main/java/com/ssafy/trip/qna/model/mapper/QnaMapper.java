package com.ssafy.trip.qna.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.trip.qna.model.QnaDto;

@Mapper
public interface QnaMapper {
	List<QnaDto> getQnaList(Map<String, Object> map) throws Exception;
	void setQnaQuestion(Map<String, String> map) throws Exception;
	void updateQnaAnswer(Map<String, String> map) throws Exception;
	QnaDto getQna(int qnaNo) throws Exception;
	void deleteQna(int qnaNo) throws Exception;
	int getListSize() throws Exception;
}
