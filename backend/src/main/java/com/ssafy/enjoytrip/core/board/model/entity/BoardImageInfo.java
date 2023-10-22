package com.ssafy.enjoytrip.core.board.model.entity;

import com.ssafy.enjoytrip.global.error.BoardException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class BoardImageInfo {

    private Long imageInfoId;
    private Long boardId;
    private String imageUrl;

    @Builder
    public BoardImageInfo(final Long imageInfoId, final Long boardId, final String imageUrl) {
        validateBoardId(boardId);
        validateFileUrl(imageUrl);
        this.imageInfoId = imageInfoId;
        this.boardId = boardId;
        this.imageUrl = imageUrl;
    }

    private void validateFileUrl(final String imageUrl) {
        if (imageUrl == null || imageUrl.isEmpty()) {
            throw new BoardException("image의 url은 null이거나 비어있을 수 없습니다.");
        }
    }

    private void validateBoardId(final Long boardId) {
        if (boardId == null) {
            throw new BoardException("boardId는 null이 될 수 없습니다.");
        }
    }

    public static BoardImageInfo of(final Long boardId, final String imageUrl) {
        return BoardImageInfo.builder()
            .boardId(boardId)
            .imageUrl(imageUrl)
            .build();
    }
}
