package com.ssafy.enjoytrip.board.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    private Long commentId;
    private Long boardId;
    private String userId;
    private String content;
    private String currentUpdate;

    public Comment(
        final Long boardId,
        final String content,
        final String userId
    ) {
        this.boardId = boardId;
        this.userId = userId;
        this.content = content;
    }
}
