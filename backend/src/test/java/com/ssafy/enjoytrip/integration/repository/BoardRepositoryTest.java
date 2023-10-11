package com.ssafy.enjoytrip.integration.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

import com.github.pagehelper.Page;
import com.ssafy.enjoytrip.core.board.dao.BoardRepository;
import com.ssafy.enjoytrip.core.board.model.dto.request.BoardSearchRequest;
import com.ssafy.enjoytrip.core.board.model.entity.Board;
import com.ssafy.enjoytrip.core.board.model.entity.BoardType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@DisplayName("BoardRepository 통합 테스트")
class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    @Sql({"/truncate.sql", "/board.sql"})
    @DisplayName("게시판 조회 테스트")
    void selectBoardTest() {
        // given & when & then
        assertThat(boardRepository.selectBoard(1L)).isNotEmpty();
    }

    @Test
    @DisplayName("게시판 등록 테스트")
    void insertBoardTest() {
        // given
        Board board = Board.builder()
            .boardType(BoardType.COMMUNITY)
            .subject("test")
            .userId("test")
            .content("test")
            .build();

        // when & then
        assertThatCode(() -> boardRepository.insertBoard(board))
            .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("게시판 필터링 조회 테스트")
    void selectBoardByFilterTest() {
        // given
        Board board = Board.builder()
            .boardType(BoardType.COMMUNITY)
            .subject("test")
            .userId("test")
            .content("test")
            .build();
        boardRepository.insertBoard(board);

        BoardSearchRequest boardSearchRequest = BoardSearchRequest.builder()
            .keyword("test")
            .category("COMMUNITY")
            .build();

        // when
        Page<Board> boards = boardRepository.selectBoardListBySearchDto(boardSearchRequest);

        // then
        assertThat(boards.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("게시판 수정 테스트")
    void modifyBoardTest() {
        // given
        Board board = Board.builder()
            .boardType(BoardType.COMMUNITY)
            .subject("test")
            .userId("test")
            .content("test")
            .build();
        boardRepository.insertBoard(board);

        // when
        Board modifiedBoard = Board.builder()
            .boardType(BoardType.COMMUNITY)
            .boardId(board.getBoardId())
            .subject("test2")
            .userId("test2")
            .content("test2")
            .build();

        // when
        boardRepository.updateBoard(modifiedBoard);

        // then
        Board newBoard = boardRepository.selectBoard(board.getBoardId())
            .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));
        assertThat(newBoard.getSubject()).isEqualTo(modifiedBoard.getSubject());
    }

    @Test
    @DisplayName("게시판 삭제 테스트")
    void deleteBoardTest() {
        // given
        Board board = Board.builder()
            .boardType(BoardType.COMMUNITY)
            .subject("test")
            .userId("test")
            .content("test")
            .build();
        boardRepository.insertBoard(board);

        // when & then
        boardRepository.deleteBoard(board.getBoardId());

        // then
        assertThat(boardRepository.selectBoard(board.getBoardId())).isEmpty();
    }


    @Test
    @DisplayName("게시판 조회수 증가 테스트")
    void updateHitTest() {
        // given
        Board board = Board.builder()
            .boardType(BoardType.COMMUNITY)
            .subject("test")
            .userId("test")
            .content("test")
            .build();
        boardRepository.insertBoard(board);

        // when & then
        boardRepository.updateHit(board.getBoardId());

        // then
        Board newBoard = boardRepository.selectBoard(board.getBoardId())
            .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));
        assertThat(newBoard).extracting("hit").isEqualTo(1L);
    }
}
