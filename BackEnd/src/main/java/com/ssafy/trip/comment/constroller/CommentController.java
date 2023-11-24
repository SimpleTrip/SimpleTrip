package com.ssafy.trip.comment.constroller;

import com.ssafy.trip.comment.model.CommentDto;
import com.ssafy.trip.comment.model.service.CommentService;
import com.ssafy.trip.common.Message;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/list/{articleId}")
    public ResponseEntity<?> getCommentList(@PathVariable("articleId") int articleId) {
        try{
            List<CommentDto> list = commentService.getCommentList(articleId);
            if (list != null && !list.isEmpty()) {
                return new ResponseEntity<>(list, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e){
            return exceptionHandling(e);
        }
    }

    @PostMapping("/write")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> setComment(@RequestBody Map<String, String> map) {
    	try {
			commentService.setComment(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return ResponseEntity.ok().body(Message.success());
    }

    @PutMapping("/rewrite")
    @PreAuthorize("isAuthenticated() and #reqMap['commentUserId'] == authentication.principal.userId")
    public ResponseEntity<?> updateComment(@RequestBody Map<String, String> reqMap) {
        try {
			commentService.updateComment(reqMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return ResponseEntity.ok().body(Message.success());
    }

    @DeleteMapping("/{commentNo}")
    @PreAuthorize("isAuthenticated() and #reqMap['commentUserId'] == authentication.principal.userId")
    public ResponseEntity<?> deleteComment(@PathVariable("commentNo") int commentNo, @RequestBody Map<String, String> reqMap) {
        try {
			commentService.deleteComment(commentNo);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return ResponseEntity.ok().body(Message.success());
    }

    private ResponseEntity<String> exceptionHandling(Exception e) {
        e.printStackTrace();
        return new ResponseEntity<>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
