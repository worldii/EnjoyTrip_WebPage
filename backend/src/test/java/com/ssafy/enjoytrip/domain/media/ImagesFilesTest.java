package com.ssafy.enjoytrip.domain.media;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.ssafy.enjoytrip.global.error.MediaException;
import com.ssafy.enjoytrip.media.model.ImageFiles;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

@DisplayName("ImagesFiles 테스트")
class ImagesFilesTest {

    @Test
    @DisplayName("정상적인 값 들어오면 예외 발생하지 않음")
    void ImagesFileSuccessTest() {
        // given
        final List<MultipartFile> images = List.of(
            new MockMultipartFile("image1", "image1.jpg", "image/jpeg", "image1".getBytes()),
            new MockMultipartFile("image2", "image2.jpg", "image/jpeg", "image2".getBytes()),
            new MockMultipartFile("image3", "image3.jpg", "image/jpeg", "image3".getBytes())
        );

        // when, then
        assertThatCode(() -> new ImageFiles(images)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("빈 값이 들어오면 예외 발생")
    void ImagesFileFailTestWithNullValue() {
        assertAll(() -> {
            assertThatCode(() -> {
                new ImageFiles(null);
            }).isInstanceOf(MediaException.class);
        });
    }
}
