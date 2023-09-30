package com.ssafy.enjoytrip.integration.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

import com.ssafy.enjoytrip.media.dao.FileRepository;
import com.ssafy.enjoytrip.media.model.entity.FileInfo;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@DisplayName("FileRepository 통합 테스트")
@SpringBootTest
class FileRepositoryTest {

    @Autowired
    private FileRepository fileRepository;

    @Test
    @Sql({"/truncate.sql", "/fileinfo.sql"})
    @DisplayName("fileinfo 조회 테스트")
    void findFileInfoTest() {
        // given
        Long boardId = 1L;

        // when
        List<FileInfo> fileInfos = fileRepository.selectFileByBoardId(boardId);

        // then
        assertThat(fileInfos.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("fileinfo 삽입 테스트")
    void insertFileInfoTest() {
        // given
        Long boardId = 1L;
        List<FileInfo> imageFiles = List.of(
            FileInfo.builder()
                .boardId(boardId)
                .fileUrl("test")
                .build(),
            FileInfo.builder()
                .boardId(boardId)
                .fileUrl("test")
                .build()
        );

        // when & then
        assertThatCode(() -> fileRepository.insertFile(boardId, imageFiles))
            .doesNotThrowAnyException();
    }

    @Test
    @Sql({"/truncate.sql", "/fileinfo.sql"})
    @DisplayName("fileinfo 삭제 테스트")
    void deleteFileInfoTest() {
        // given
        Long boardId = 1L;

        // when
        fileRepository.deleteFileByBoardId(boardId);

        // then
        assertThat(fileRepository.selectFileByBoardId(boardId).size()).isEqualTo(0);
    }
}
