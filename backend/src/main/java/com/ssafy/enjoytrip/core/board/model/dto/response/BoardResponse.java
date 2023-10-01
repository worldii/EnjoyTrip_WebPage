package com.ssafy.enjoytrip.core.board.model.dto.response;

import com.ssafy.enjoytrip.core.board.model.entity.Board;
import com.ssafy.enjoytrip.core.board.model.entity.BoardType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BoardResponse {

    private Long boardId;
    private String userId;
    private String subject;
    private String content;
    private BoardType boardType;
    private Long hit;
    private String currentUpdate;

    public static BoardResponse from(final Board board) {
        return BoardResponse.builder()
            .boardId(board.getBoardId())
            .userId(board.getUserId())
            .subject(board.getSubject())
            .content(board.getContent())
            .boardType(board.getBoardType())
            .hit(board.getHit())
            .currentUpdate(board.getCurrentUpdate())
            .build();
    }
}
