package com.ssafy.trip.qna.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ssafy.trip.qna.exception.QnaErrorCode;
import com.ssafy.trip.qna.exception.QnaException;
import com.ssafy.trip.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.ssafy.trip.qna.model.QnaDto;
import com.ssafy.trip.qna.model.mapper.QnaMapper;

@Service
@RequiredArgsConstructor
public class QnaServiceImpl implements QnaService {
	private final QnaMapper qnaMapper;

	@Override
	public Map<String, Object> getQnaList(Map<String, Object> map) {
		Map<String, Object> result = new HashMap<>();
		Map<String, Object> params = PageUtil.getStartAndListSize(map);
		int totalQnaCount = getListSize(params);
		int totalPageCount = (totalQnaCount - 1) / 10 + 1;
		List<QnaDto> list = qnaMapper.getQnaList(params);
		result.put("totalPgno", totalPageCount);
		result.put("qnaList", list);
		return result;
	}

	@Override
	public void setQnaQuestion(Map<String, String> map) {
		qnaMapper.setQnaQuestion(map);
	}

	@Override
	public void updateQnaAnswer(Map<String, String> map) {
		getQna(Integer.parseInt(map.get("qnaNo")));
		qnaMapper.updateQnaAnswer(map);
	}

	@Override
	public QnaDto getQna(int qnaNo) {
		QnaDto qnaDto = qnaMapper.getQna(qnaNo);
		if(qnaDto == null) throw new QnaException(QnaErrorCode.NO_QNA);
		return qnaDto;
	}

	@Override
	public void deleteQna(int qnaNo) {
		getQna(qnaNo);
		qnaMapper.deleteQna(qnaNo);
	}

	@Override
	public int getListSize(Map<String, Object> map) {
		return qnaMapper.getListSize(map);
	}
}
