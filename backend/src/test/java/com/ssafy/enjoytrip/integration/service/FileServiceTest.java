package com.ssafy.enjoytrip.integration.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.ssafy.enjoytrip.media.model.entity.FileInfo;
import com.ssafy.enjoytrip.media.service.FileService;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@DisplayName("FileService 통합 테스트")
@SpringBootTest
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
        List<FileInfo> fileInfos = fileService.selectFile(boardId);

        // then
        assertThat(fileInfos).isNotNull();
    }

    @Test
    @DisplayName("fileService 정상 등록 테스트")
    void fileServiceTest() {
        // given
        Long boardId = 1L;
        int currentSize = fileService.selectFile(boardId).size();

        FileInfo fileInfo = FileInfo.builder()
            .boardId(boardId)
            .fileUrl("test")
            .build();

        // when
        fileService.insertFile(boardId, List.of(fileInfo.getFileUrl()));

        // then
        List<FileInfo> fileInfos = fileService.selectFile(boardId);
        assertThat(fileInfos.size()).isEqualTo(currentSize + 1);
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
        List<FileInfo> fileInfos = fileService.selectFile(boardId);
        assertThat(fileInfos.size()).isEqualTo(currentSize - 1);
    }
}
