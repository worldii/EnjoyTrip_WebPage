package com.ssafy.enjoytrip.integration.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

import com.ssafy.enjoytrip.core.hotplace.model.dao.HotPlaceArticleImageRepository;
import com.ssafy.enjoytrip.core.hotplace.model.entity.HotPlaceArticleImage;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@DisplayName("HotPlaceArticleImageRepository 테스트")
class HotPlaceArticleImageRepositoryTest {

    @Autowired private HotPlaceArticleImageRepository hotPlaceArticleImageRepository;

    @Test
    @DisplayName("HotPlaceArticleImage를 생성한다.")
    @Sql({"/truncate.sql"})
    void insertHotPlaceArticleImage() {
        // given
        HotPlaceArticleImage hotPlaceArticleImageInfo =
                HotPlaceArticleImage.builder()
                        .hotPlaceArticleId(1L)
                        .imageUrl("www.naver.com")
                        .build();

        // when & then
        assertThatCode(
                        () ->
                                hotPlaceArticleImageRepository.insertFile(
                                        List.of(hotPlaceArticleImageInfo)))
                .doesNotThrowAnyException();
    }
}
