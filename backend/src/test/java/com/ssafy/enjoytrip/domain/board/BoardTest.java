package com.ssafy.enjoytrip.domain.board;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

import com.ssafy.enjoytrip.core.board.model.entity.Board;
import com.ssafy.enjoytrip.core.board.model.entity.BoardType;
import com.ssafy.enjoytrip.global.error.BoardException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Board 도메인 테스트")
class BoardTest {

    @Test
    @DisplayName("게시판 생성 테스트")
    void createBoard() {
        // given
        assertThatCode(
            () ->
                Board.builder()
                    .boardType(BoardType.COMMUNITY)
                    .subject("test")
                    .userId("test")
                    .content("test")
                    .imageUrls(null)
                    .build()
        ).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("게시판 생성 시 제목이 없으면 예외 발생")
    void createBoardWithoutSubject() {
        // given & when & then
        assertThatCode(() -> Board.builder()
            .boardType(BoardType.COMMUNITY)
            .subject(null)
            .userId("test")
            .content("test")
            .imageUrls(null)
            .build())
            .isInstanceOf(BoardException.class)
            .hasMessage("제목이 없습니다.");
    }

    @Test
    @DisplayName("게시판 생성 시 내용이 없으면 예외 발생")
    void createBoardWithoutContent() {
        // given & when & then
        assertThatCode(() -> Board.builder()
            .boardType(BoardType.COMMUNITY)
            .subject("test")
            .userId("test")
            .content(null)
            .imageUrls(null)
            .build())
            .isInstanceOf(BoardException.class)
            .hasMessage("내용이 없습니다.");
    }

    @Test
    @DisplayName("게시판 생성 시 작성자가 없으면 예외 발생")
    void createBoardWithoutUserId() {
        // given & when & then
        assertThatCode(() -> Board.builder()
            .boardType(BoardType.COMMUNITY)
            .subject("test")
            .userId(null)
            .content("test")
            .imageUrls(null)
            .build())
            .isInstanceOf(BoardException.class)
            .hasMessage("유저 아이디가 없습니다.");
    }
}
