package com.ssafy.enjoytrip.integration.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

import com.ssafy.enjoytrip.config.UploadConfig;
import com.ssafy.enjoytrip.core.media.service.MediaService;
import com.ssafy.enjoytrip.global.error.MediaException;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

@SpringBootTest
@DisplayName("MediaService 통합 테스트")
@Import(UploadConfig.class)
class MediaServiceTest {

    @Autowired
    private MediaService mediaService;

    @Test
    @DisplayName("파일 업로드 테스트")
    void uploadFileTest() {
        // given
        Long boardId = 1L;
        String userId = "test";
        List<MultipartFile> imagesFiles = List.of(
            new MockMultipartFile("image1", "image1.jpg", "image/jpeg", "image1".getBytes()),
            new MockMultipartFile("image2", "image2.jpg", "image/jpeg", "image2".getBytes()),
            new MockMultipartFile("image3", "image3.jpg", "image/jpeg", "image3".getBytes())
        );
        String folderName = null;

        // when & then
        assertThatCode(() -> mediaService.insertMedias(boardId, userId, imagesFiles, folderName))
            .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("boardId가 null일 때, 파일 업로드 실패")
    void uploadFileFailTestWithNoBoardId() {
        // given
        Long boardId = null;
        String userId = "test";
        List<MultipartFile> imagesFiles = List.of(
            new MockMultipartFile("image1", "image1.jpg", "image/jpeg", "image1".getBytes()),
            new MockMultipartFile("image2", "image2.jpg", "image/jpeg", "image2".getBytes()),
            new MockMultipartFile("image3", "image3.jpg", "image/jpeg", "image3".getBytes())
        );
        String folderName = null;

        // when & then
        assertThatCode(() -> mediaService.insertMedias(boardId, userId, imagesFiles, folderName))
            .isInstanceOf(MediaException.class)
            .hasMessage("파일 업로드에 실패했습니다.");
    }

    @Test
    @DisplayName("파일이 없을 때, 파일 업로드 실패")
    void uploadFileFailTestWithNoFiles() {
        // given
        Long boardId = 1L;
        String userId = "test";
        List<MultipartFile> imagesFiles = null;
        String folderName = null;

        // when & then
        assertThatCode(() -> mediaService.insertMedias(boardId, userId, imagesFiles, folderName))
            .isInstanceOf(MediaException.class)
            .hasMessage("imageFiles은 null이 될 수 없습니다.");
    }
}
