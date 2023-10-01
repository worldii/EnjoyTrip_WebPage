package com.ssafy.enjoytrip.core.board.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class CommentModifyRequest {

    private Long boardId;
    private String content;
}
