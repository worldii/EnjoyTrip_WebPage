package com.ssafy.enjoytrip.domain.media;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

import com.ssafy.enjoytrip.core.media.model.entity.FileInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;

@DisplayName("FileInfo 테스트")
class FileInfoTest {

    @Test
    @DisplayName("fileInfo 정상적으로 생성")
    void fileInfoCreate() {
        // given
        assertThatCode(() -> FileInfo.builder()
            .fileUrl("https://www.naver.com")
            .boardId(1L)
            .userId("test")
            .build()).doesNotThrowAnyException();
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("fileInfo 의 fileUrl이 null일 때 예외 발생")
    void fileInfoCreateFailWithFileUrlNull(String fileUrl) {
        // given
        assertThatCode(() -> FileInfo.builder()
            .fileUrl(fileUrl)
            .userId("test")
            .boardId(1L)
            .build()).hasMessage("file의 url은 null이거나 비어있을 수 없습니다.");
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("fileInfo 의 boardId가 null일 때 예외 발생")
    void fileInfoCreateFailWithBoardIdNull(Long boardId) {
        // given
        assertThatCode(() -> FileInfo.builder()
            .fileUrl("https://www.naver.com")
            .boardId(boardId)
            .userId("test")
            .build()).hasMessage("boardId는 null이 될 수 없습니다.");
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("fileInfo 의 userId가 null일 때 예외 발생")
    void fileInfoCreateFailWithUserIdNull(String userId) {
        // given
        assertThatCode(() -> FileInfo.builder()
            .fileUrl("https://www.naver.com")
            .boardId(1L)
            .userId(userId)
            .build())
            .hasMessage("userId는 null이거나 비어있을 수 없습니다.");
    }
}
