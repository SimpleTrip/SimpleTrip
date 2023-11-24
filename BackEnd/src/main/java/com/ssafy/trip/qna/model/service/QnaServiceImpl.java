package com.ssafy.trip.qna.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.trip.qna.model.QnaDto;
import com.ssafy.trip.qna.model.mapper.QnaMapper;

@Service
public class QnaServiceImpl implements QnaService {
	private QnaMapper qnaMapper;

	@Autowired
	public QnaServiceImpl(QnaMapper qnaMapper) {
		this.qnaMapper = qnaMapper;
	}
	
	@Override
	public List<QnaDto> getQnaList(Map<String, Object> map) throws Exception {
		return qnaMapper.getQnaList(map);
	}

	@Override
	public void setQnaQuestion(Map<String, String> map) throws Exception {
		qnaMapper.setQnaQuestion(map);
	}

	@Override
	public void updateQnaAnswer(Map<String, String> map) throws Exception {
		qnaMapper.updateQnaAnswer(map);
	}

	@Override
	public QnaDto getQna(int qnaNo) throws Exception {
		return qnaMapper.getQna(qnaNo);
	}

	@Override
	public void deleteQna(int qnaNo) throws Exception {
		qnaMapper.deleteQna(qnaNo);
	}

	@Override
	public int getListSize() throws Exception {
		return qnaMapper.getListSize();
	}
}
