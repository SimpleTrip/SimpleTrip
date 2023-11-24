package com.ssafy.trip.qna.controller;

import java.util.Map;
import com.ssafy.trip.common.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.ssafy.trip.qna.model.service.QnaService;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/qna")
public class QnaController {
    private final QnaService qnaService;

    @GetMapping(value = "/qnaList")
    public ResponseEntity<?> getQnaList(@RequestParam Map<String, Object> map) {
        return ResponseEntity.ok().body(Message.success(qnaService.getQnaList(map)));
    }

    @PostMapping(value = "/question")
    @PreAuthorize("isAuthenticated() and #map['questionUserId'] == authentication.principal.userId")
    public ResponseEntity<?> setQnaQuestion(@RequestBody Map<String, String> map) {
        qnaService.setQnaQuestion(map);
        return ResponseEntity.ok().body(Message.success());
    }

    @PutMapping(value = "/answer")
    @PreAuthorize("isAuthenticated() and hasRole('ADMIN')")
    public ResponseEntity<?> updateQnaAnswer(@RequestBody Map<String, String> map) {
        qnaService.updateQnaAnswer(map);
        return ResponseEntity.ok().body(Message.success());
    }

    @GetMapping(value = "/question/{qnaNo}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getQna(@PathVariable("qnaNo") int qnaNo) {
        return ResponseEntity.ok().body(Message.success(qnaService.getQna(qnaNo)));
    }

    @DeleteMapping(value = "/question/{qnaNo}")
    @PreAuthorize("(isAuthenticated() and #map['questionUserId'] == authentication.principal.userId) or hasRole('ADMIN')")
    public ResponseEntity<?> deleteQna(@PathVariable("qnaNo") int qnaNo, @RequestBody Map<String, String> map) {
        qnaService.deleteQna(qnaNo);
        return ResponseEntity.ok().body(Message.success());
    }
}
