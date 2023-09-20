package com.ssafy.enjoytrip.board.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comment {

    private Long commentId;
    private Long boardId;
    private String userId;
    private String content;
    private String currentUpdate;
}
