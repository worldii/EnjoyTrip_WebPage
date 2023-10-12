package com.ssafy.enjoytrip.domain.board;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

import com.ssafy.enjoytrip.core.board.model.entity.BoardImageInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;

@DisplayName("imageInfo 테스트")
class BoardImageInfoTest {

    @Test
    @DisplayName("imageInfo 정상적으로 생성")
    void fileInfoCreate() {
        // given
        assertThatCode(() -> BoardImageInfo.builder()
            .imageUrl("https://www.naver.com")
            .boardId(1L)
            .build()).doesNotThrowAnyException();
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("imageInfo 의 imageUrl이 null일 때 예외 발생")
    void fileInfoCreateFailWithFileUrlNull(String fileUrl) {
        // given
        assertThatCode(() -> BoardImageInfo.builder()
            .imageUrl(fileUrl)
            .boardId(1L)
            .build())
            .hasMessage("image의 url은 null이거나 비어있을 수 없습니다.");
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("imageInfo 의 boardId가 null일 때 예외 발생")
    void fileInfoCreateFailWithBoardIdNull(Long boardId) {
        // given
        assertThatCode(() -> BoardImageInfo.builder()
            .imageUrl("https://www.naver.com")
            .boardId(boardId)
            .build()).hasMessage("boardId는 null이 될 수 없습니다.");
    }
}
