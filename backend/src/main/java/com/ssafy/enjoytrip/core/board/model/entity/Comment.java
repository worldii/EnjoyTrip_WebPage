package com.ssafy.enjoytrip.core.board.model.entity;

import com.ssafy.enjoytrip.global.error.BoardException;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Comment {

    private Long commentId;
    private Long boardId;
    private String userId;
    private String content;
    // TODO: 설정해줘야 함
    private String currentUpdate;

    @Builder
    public Comment(
        final Long commentId, final Long boardId,
        final String userId, final String content
    ) {
        validateBoardId(boardId);
        validateUserId(userId);
        validateContent(content);
        this.commentId = commentId;
        this.boardId = boardId;
        this.userId = userId;
        this.content = content;
    }

    private void validateBoardId(Long boardId) {
        if (boardId == null) {
            throw new BoardException("게시글 번호가 없습니다.");
        }
    }

    private void validateUserId(String userId) {
        if (userId == null) {
            throw new BoardException("유저 아이디가 없습니다.");
        }
    }

    private void validateContent(String content) {
        if (content == null) {
            throw new BoardException("댓글 내용이 없습니다.");
        }
    }
}
