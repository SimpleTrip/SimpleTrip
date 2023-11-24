package com.ssafy.trip.comment.model;

import lombok.Data;

@Data
public class CommentDto {
    private int articleId;
    private int commentNo;
    private String commentContent;
    private String commentUserId;
}
