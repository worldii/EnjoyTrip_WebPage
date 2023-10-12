package com.ssafy.enjoytrip.integration.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

import com.ssafy.enjoytrip.core.board.model.dto.request.BoardImageInfoResponse;
import com.ssafy.enjoytrip.core.board.model.entity.BoardImageInfo;
import com.ssafy.enjoytrip.core.board.service.BoardImageService;
import com.ssafy.enjoytrip.global.error.BoardException;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@DisplayName("FileService 통합 테스트")
@Sql({"/truncate.sql", "/boardImageInfo.sql"})
class BoardImageServiceTest {

    @Autowired
    private BoardImageService fileService;

    @Test
    @DisplayName("fileInfo 정상 조회 테스트")
    void fileInfoTest() {
        // given
        Long boardId = 1L;

        // when
        List<BoardImageInfoResponse> fileInfos = fileService.selectBoardImage(boardId);

        // then
        assertThat(fileInfos).isNotNull();
    }

    @Test
    @DisplayName("fileService 정상 등록 테스트")
    void fileServiceTest() {
        // given
        Long boardId = 1L;
        int currentSize = fileService.selectBoardImage(boardId).size();

        BoardImageInfo boardImageInfo = BoardImageInfo.builder()
            .boardId(boardId)
            .imageUrl("test")
            .build();

        // when
        fileService.insertBoardImage(boardId, List.of(boardImageInfo.getImageUrl()));

        // then
        List<BoardImageInfoResponse> fileInfos = fileService.selectBoardImage(boardId);
        assertThat(fileInfos.size()).isEqualTo(currentSize + 1);
    }

    @Test
    @DisplayName("fileService는 boardId가 null일 경우 예외를 던진다.")
    void fileServiceTestWithNullBoardId() {
        // given
        Long boardId = null;
        List<String> fileUrls = List.of("test");

        // when & then
        assertThatCode(() -> fileService.insertBoardImage(boardId, fileUrls))
            .isInstanceOf(BoardException.class)
            .hasMessage("boardId는 null이 될 수 없습니다.");
    }

    @Test
    @DisplayName("fileService 정상 삭제 테스트")
    void fileServiceDeleteTest() {
        // given
        Long boardId = 1L;
        int currentSize = fileService.selectBoardImage(boardId).size();

        // when
        fileService.deleteBoardImage(boardId);

        // then
        List<BoardImageInfoResponse> fileInfos = fileService.selectBoardImage(boardId);
        assertThat(fileInfos.size()).isEqualTo(currentSize - 1);
    }
}
