package com.ssafy.enjoytrip.board.model.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Comment {
    private int commentId;
    private int boardId;
    private String userId;
    private String content;
    private String currentUpdate;

    public Comment (CommentRequestDto commentRequestDto, String userId, int boardId) {
        this.userId = userId;
        this.content = commentRequestDto.getContent();
        this.boardId = boardId;
    }
}
