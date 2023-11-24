package com.ssafy.trip.comment.model.service;

import com.ssafy.trip.comment.model.CommentDto;
import com.ssafy.trip.comment.model.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentMapper commentMapper;

    @Override
    public List<CommentDto> getCommentList(int articleId) throws Exception {
        return commentMapper.getCommentList(articleId);
    }

    @Override
    public void setComment(Map<String, String> map) throws Exception {
        commentMapper.setComment(map);
    }

    @Override
    public void updateComment(Map<String, String> map) throws Exception {
        commentMapper.updateComment(map);
    }

    @Override
    public void deleteComment(int commentNo) throws Exception {
        commentMapper.deleteComment(commentNo);
    }
}
