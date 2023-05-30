package com.ssafy.enjoytrip.board.model.dto;

import lombok.Data;
@Data
public class CommentResponseDto {

    private int commentId;
    private String userId;
    private String content;
    private String currentUpdate;
    public CommentResponseDto() {
    }

    public CommentResponseDto(int commentId, String userId, String content, String currentUpdate) {
        this.commentId = commentId;
        this.userId = userId;
        this.content = content;
        this.currentUpdate = currentUpdate;
    }

    public CommentResponseDto(Comment comment) {
        this.commentId = comment.getCommentId();
        this.userId = comment.getUserId();
        this.content = comment.getContent();
        this.currentUpdate = comment.getCurrentUpdate();
    }
}
