package com.ssafy.enjoytrip.domain.board;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

import com.ssafy.enjoytrip.core.board.model.entity.Comment;
import com.ssafy.enjoytrip.global.error.BoardException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Comment 도메인 테스트")
class CommentTest {

    @Test
    @DisplayName("Comment를 정상적으로 생성하는 테스트")
    void createComment() {
        // given & when & then
        assertThatCode(() -> {
            Comment.builder()
                .commentId(1L)
                .boardId(1L)
                .userId("test")
                .content("test")
                .build();
        }).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("boardId가 null일 경우 Comment를 생성할 수 없다.")
    void createCommentWithNullBoardId() {
        // given & when & then
        assertThatCode(() -> {
            Comment.builder()
                .commentId(1L)
                .userId("test")
                .content("test")
                .build();
        })
            .isInstanceOf(BoardException.class)
            .hasMessage("게시글 번호가 없습니다.");
    }

    @Test
    @DisplayName("userId가 null일 경우 Comment를 생성할 수 없다.")
    void createCommentWithNullUserId() {
        // given & when & then
        assertThatCode(() -> {
            Comment.builder()
                .commentId(1L)
                .boardId(1L)
                .content("test")
                .build();
        })
            .isInstanceOf(BoardException.class)
            .hasMessage("유저 아이디가 없습니다.");
    }

    @Test
    @DisplayName("content가 null일 경우 Comment를 생성할 수 없다.")
    void createCommentWithNullContent() {
        // given & when & then
        assertThatCode(() -> {
            Comment.builder()
                .commentId(1L)
                .boardId(1L)
                .userId("test")
                .build();
        })
            .isInstanceOf(BoardException.class)
            .hasMessage("댓글 내용이 없습니다.");
    }
}
