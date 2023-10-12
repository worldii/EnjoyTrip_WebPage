package com.ssafy.enjoytrip.integration.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

import com.ssafy.enjoytrip.core.media.model.dto.FileInfoResponse;
import com.ssafy.enjoytrip.core.media.model.entity.FileInfo;
import com.ssafy.enjoytrip.core.media.service.FileService;
import com.ssafy.enjoytrip.global.error.MediaException;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@DisplayName("FileService 통합 테스트")
@Sql({"/truncate.sql", "/fileInfo.sql"})
class FileServiceTest {

    @Autowired
    private FileService fileService;

    @Test
    @DisplayName("fileInfo 정상 조회 테스트")
    void fileInfoTest() {
        // given
        Long boardId = 1L;

        // when
        List<FileInfoResponse> fileInfos = fileService.selectFile(boardId);

        // then
        assertThat(fileInfos).isNotNull();
    }

    @Test
    @DisplayName("fileService 정상 등록 테스트")
    void fileServiceTest() {
        // given
        Long boardId = 1L;
        String userId = "test";
        int currentSize = fileService.selectFile(boardId).size();

        FileInfo fileInfo = FileInfo.builder()
            .boardId(boardId)
            .fileUrl("test")
            .userId(userId)
            .build();

        // when
        fileService.insertFile(boardId, userId, List.of(fileInfo.getFileUrl()));

        // then
        List<FileInfoResponse> fileInfos = fileService.selectFile(boardId);
        assertThat(fileInfos.size()).isEqualTo(currentSize + 1);
    }

    @Test
    @DisplayName("fileService는 boardId가 null일 경우 예외를 던진다.")
    void fileServiceTestWithNullBoardId() {
        // given
        Long boardId = null;
        String userId = "test";
        List<String> fileUrls = List.of("test");

        // when & then
        assertThatCode(() -> fileService.insertFile(boardId, userId, fileUrls))
            .isInstanceOf(MediaException.class)
            .hasMessage("boardId는 null이 될 수 없습니다.");
    }

    @Test
    @DisplayName("fileService 정상 삭제 테스트")
    void fileServiceDeleteTest() {
        // given
        Long boardId = 1L;
        int currentSize = fileService.selectFile(boardId).size();

        // when
        fileService.deleteFile(boardId);

        // then
        List<FileInfoResponse> fileInfos = fileService.selectFile(boardId);
        assertThat(fileInfos.size()).isEqualTo(currentSize - 1);
    }
}
