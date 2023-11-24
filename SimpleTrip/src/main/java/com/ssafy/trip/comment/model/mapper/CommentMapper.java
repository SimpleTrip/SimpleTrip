package com.ssafy.trip.comment.model.mapper;

import com.ssafy.trip.comment.model.CommentDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CommentMapper {
    List<CommentDto> getCommentList(int articleId) throws Exception;
    void setComment(Map<String, String> map) throws  Exception;
    void updateComment(Map<String, String> map) throws Exception;
    void deleteComment(int commentNo) throws Exception;
}
