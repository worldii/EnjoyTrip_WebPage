package com.ssafy.enjoytrip.board.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CommentModifyRequest {

    private Long commentId;
    private Long boardId;
    private String userId;
    private String content;
}
