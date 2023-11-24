package com.ssafy.trip.comment.model.service;

import com.ssafy.trip.comment.model.CommentDto;

import java.util.List;
import java.util.Map;

public interface CommentService {
    List<CommentDto> getCommentList(int articleId) throws Exception;
    void setComment(Map<String, String> map) throws  Exception;
    void updateComment(Map<String, String> map) throws Exception;
    void deleteComment(int commentNo) throws Exception;
}
