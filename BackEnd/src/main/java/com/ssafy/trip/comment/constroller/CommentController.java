package com.ssafy.trip.comment.constroller;

import com.ssafy.trip.comment.model.CommentDto;
import com.ssafy.trip.comment.model.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
                return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e){
            return exceptionHandling(e);
        }
    }

    @PostMapping("/write")
    public ResponseEntity<?> setComment(@RequestBody Map<String, String> map) {
        try {
            commentService.setComment(map);
            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @PutMapping("/rewrite")
    public ResponseEntity<?> updateComment(@RequestBody Map<String, String> map) {
        try {
            commentService.updateComment(map);
            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @DeleteMapping("/{commentNo}")
    public ResponseEntity<?> deleteQna(@PathVariable("commentNo") int commentNo) {
        try {
            commentService.deleteComment(commentNo);
            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    private ResponseEntity<String> exceptionHandling(Exception e) {
        e.printStackTrace();
        return new ResponseEntity<>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
