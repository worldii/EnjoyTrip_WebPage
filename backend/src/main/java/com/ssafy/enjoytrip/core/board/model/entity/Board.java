package com.ssafy.enjoytrip.core.board.model.entity;

import com.ssafy.enjoytrip.global.error.BoardException;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Board {

    private Long boardId;
    private String userId;
    private String subject;
    private String content;
    private Long hit;
    private String currentUpdate;
    private BoardType boardType;
    private List<String> imageUrls;

    @Builder
    public Board(
        final Long boardId, final String userId, final String subject,
        final String content, final Long hit, final String currentUpdate,
        final BoardType boardType, final List<String> imageUrls
    ) {
        validateUserId(userId);
        validateSubject(subject);
        validateContent(content);

        this.boardId = boardId;
        this.userId = userId;
        this.subject = subject;
        this.content = content;
        this.hit = hit;
        this.currentUpdate = currentUpdate;
        this.boardType = boardType;
        this.imageUrls = imageUrls;
    }

    private void validateUserId(String userId) {
        if (userId == null || userId.isEmpty()) {
            throw new BoardException("유저 아이디가 없습니다.");
        }
    }

    private void validateSubject(String subject) {
        if (subject == null || subject.isEmpty()) {
            throw new BoardException("제목이 없습니다.");
        }
    }

    private void validateContent(String content) {
        if (content == null || content.isEmpty()) {
            throw new BoardException("내용이 없습니다.");
        }
    }

    public boolean isImageUrlNotEmpty() {
        return !isImageUrlsEmpty();
    }

    private boolean isImageUrlsEmpty() {
        return imageUrls == null || imageUrls.isEmpty();
    }
}
