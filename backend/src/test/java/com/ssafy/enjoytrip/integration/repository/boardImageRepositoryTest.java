package com.ssafy.enjoytrip.integration.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

import com.ssafy.enjoytrip.core.board.dao.BoardImageRepository;
import com.ssafy.enjoytrip.core.board.model.entity.BoardImageInfo;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@DisplayName("FileRepository 통합 테스트")
@SpringBootTest
class boardImageRepositoryTest {

    @Autowired private BoardImageRepository boardImageRepository;

    @Test
    @Sql({"/truncate.sql", "/boardImageInfo.sql"})
    @DisplayName("fileinfo 조회 테스트")
    void findFileInfoTest() {
        // given
        Long boardId = 1L;

        // when
        List<BoardImageInfo> boardImageInfos = boardImageRepository.selectFileByBoardId(boardId);

        // then
        assertThat(boardImageInfos.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("fileinfo 삽입 테스트")
    @Sql({"/truncate.sql", "/boardImageInfo.sql"})
    void insertFileInfoTest() {
        // given
        Long boardId = 1L;
        List<BoardImageInfo> imageFiles =
                List.of(
                        BoardImageInfo.builder().boardId(boardId).imageUrl("test").build(),
                        BoardImageInfo.builder().boardId(boardId).imageUrl("test").build());

        // when & then
        assertThatCode(() -> boardImageRepository.insertFile(boardId, imageFiles))
                .doesNotThrowAnyException();
    }

    @Test
    @Sql({"/truncate.sql", "/boardImageInfo.sql"})
    @DisplayName("fileinfo 삭제 테스트")
    void deleteFileInfoTest() {
        // given
        Long boardId = 1L;

        // when
        boardImageRepository.deleteFileByBoardId(boardId);

        // then
        assertThat(boardImageRepository.selectFileByBoardId(boardId).size()).isZero();
    }
}
