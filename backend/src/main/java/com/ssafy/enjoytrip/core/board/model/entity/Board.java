package com.ssafy.enjoytrip.core.board.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Board {

    private Long boardId;
    private String userId;
    private String subject;
    private String content;
    private Long hit;
    // TODO : 수정해야 함
    private String currentUpdate;
    private BoardType boardType;
}
