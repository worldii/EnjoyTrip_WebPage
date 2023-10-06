package com.ssafy.enjoytrip.integration.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.enjoytrip.config.UploadConfig;
import com.ssafy.enjoytrip.core.board.model.dto.request.BoardSaveRequest;
import com.ssafy.enjoytrip.core.board.model.dto.request.PageInfoRequest;
import com.ssafy.enjoytrip.core.board.model.dto.response.BoardDetailResponse;
import com.ssafy.enjoytrip.core.board.model.dto.response.PageResponse;
import com.ssafy.enjoytrip.core.board.model.entity.BoardType;
import com.ssafy.enjoytrip.core.board.service.BoardService;
import com.ssafy.enjoytrip.global.error.BoardException;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.multipart.MultipartFile;

@SpringBootTest
@DisplayName("BoardService 통합 테스트")
@Import(UploadConfig.class)
class BoardServiceTest {

    @Autowired
    private BoardService boardService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Sql({"/truncate.sql", "/user.sql"})
    @DisplayName("게시판 등록 테스트")
    void insertBoardTest() throws JsonProcessingException {
        // given
        String userId = "test";
        BoardSaveRequest boardSaveRequest = BoardSaveRequest.builder()
            .boardType(BoardType.NOTICE)
            .content("test")
            .subject("test")
            .build();
        String json = objectMapper.writeValueAsString(boardSaveRequest);
        final List<MultipartFile> images = List.of(
            new MockMultipartFile("image1", "image1.jpg", "image/jpeg", "image1".getBytes()),
            new MockMultipartFile("image2", "image2.jpg", "image/jpeg", "image2".getBytes()),
            new MockMultipartFile("image3", "image3.jpg", "image/jpeg", "image3".getBytes())
        );

        // when & then
        assertThatCode(
            () -> boardService.saveBoard(json, images, userId)).doesNotThrowAnyException();
    }


    @Test
    @Sql({"/truncate.sql", "/user.sql"})
    @DisplayName("게시판 등록 실패 테스트 : 유저가 없을 때")
    void insertBoardFailTest() throws JsonProcessingException {
        // given
        String wrongUserId = "wrong";
        BoardSaveRequest boardSaveRequest = BoardSaveRequest.builder()
            .boardType(BoardType.NOTICE)
            .content("test")
            .subject("test")
            .build();
        String json = objectMapper.writeValueAsString(boardSaveRequest);
        final List<MultipartFile> images = List.of(
            new MockMultipartFile("image1", "image1.jpg", "image/jpeg", "image1".getBytes()),
            new MockMultipartFile("image2", "image2.jpg", "image/jpeg", "image2".getBytes()),
            new MockMultipartFile("image3", "image3.jpg", "image/jpeg", "image3".getBytes())
        );

        // when & then
        assertThatCode(
            () -> boardService.saveBoard(json, images, wrongUserId))
            .isInstanceOf(BoardException.class)
            .hasMessage("해당 userId에 해당하는 user가 없습니다.");
    }

    @Test
    @DisplayName("게시판 조회 테스트")
    void selectBoardTest() {
        // when
        PageInfoRequest pageInfoRequest = PageInfoRequest.builder()
            .page(1)
            .pageSize(1)
            .build();

        // when
        PageResponse boardList = boardService.getBoardList(pageInfoRequest);

        // then
        assertThat(boardList).isNotNull();
    }


    @Test
    @Sql({"/truncate.sql", "/user.sql", "/board.sql"})
    @DisplayName("게시판 상세 조회 테스트")
    void selectBoardDetailTest() {
        // given
        Long boardId = 1L;

        // when
        BoardDetailResponse detail = boardService.detail(boardId);

        // then
        assertThat(detail.getBoardId()).isEqualTo(boardId);
    }
}
