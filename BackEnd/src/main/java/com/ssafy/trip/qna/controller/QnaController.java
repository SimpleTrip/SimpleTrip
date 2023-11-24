package com.ssafy.trip.qna.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ssafy.trip.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ssafy.trip.qna.model.QnaDto;
import com.ssafy.trip.qna.model.service.QnaService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/qna")
public class QnaController {
	private final QnaService qnaService;

	@GetMapping(value = "/qnaList")
	public ResponseEntity<?> getQnaList(@RequestParam Map<String, String> map) {
		try {
			Map<String, Object> result = new HashMap<>();
			Map<String, Object> params = PageUtil.getStartAndListSize(map);
			int totalQnaCount = qnaService.getListSize();
			int totalPageCount = (totalQnaCount - 1) / 10 + 1;
			List<QnaDto> list = qnaService.getQnaList(params);

			result.put("totalPgno", totalPageCount);
			result.put("qnaList", list);
			if (list != null && !list.isEmpty()) {
				return new ResponseEntity<>(result, HttpStatus.OK);
			} else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	// @RequestParam Map<String, String> map 으로 가져올 수도 있고,
	// DTO로 가져올 수도 있기 때문에 우선은 map으로 처리하고 restAPI를 만든다.
	@PostMapping(value = "/question")
	public ResponseEntity<?> setQnaQuestion(@RequestBody Map<String, String> map) {
		try {
			qnaService.setQnaQuestion(map);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	// @RequestParam Map<String, String> map 으로 가져올 수도 있고,
	// DTO로 가져올 수도 있기 때문에 우선은 map으로 처리하고 restAPI를 만든다.
	// 삭제 시에는 map의 answerAdminId, answer를 빈 값으로 보내 처리한다.
	// 수정, 삭제, 작성 모두 update로직을 통한다.
	@PutMapping(value = "/answer")
	public ResponseEntity<?> updateQnaAnswer(@RequestBody Map<String, String> map) {
		try {
			qnaService.updateQnaAnswer(map);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@GetMapping(value = "/question/{qnaNo}")
	public ResponseEntity<?> getQna(@PathVariable("qnaNo") int qnaNo) {
		try {
			QnaDto qnaDto = qnaService.getQna(qnaNo);
			return new ResponseEntity<QnaDto>(qnaDto, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@DeleteMapping(value = "/question/{qnaNo}")
	public ResponseEntity<?> deleteQna(@PathVariable("qnaNo") int qnaNo) {
		try {
			qnaService.deleteQna(qnaNo);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
