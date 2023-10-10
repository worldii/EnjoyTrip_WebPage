package com.ssafy.enjoytrip.integration.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.ssafy.enjoytrip.core.hotplace.model.dao.HotPlaceArticleRepository;
import com.ssafy.enjoytrip.core.hotplace.model.entity.HotPlaceArticle;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@DisplayName("HotPlaceArticleRepository 테스트")
class HotPlaceArticleRepositoryTest {

    @Autowired
    private HotPlaceArticleRepository hotPlaceArticleRepository;

    @Test
    @DisplayName("HotPlaceArticle를 조회한다.")
    @Sql({"/truncate.sql", "/hotPlaceArticle.sql"})
    void selectHotPlaceArticleByArticleId() {
        // given
        Long hotPlaceArticleId = 1L;

        // when
        Optional<HotPlaceArticle> hotPlace = hotPlaceArticleRepository
            .selectHotPlaceArticleByArticleId(hotPlaceArticleId);

        // then
        assertThat(hotPlace).isNotNull();
    }

    @Test
    @DisplayName("HotPlaceArticle를 생성한다.")
    @Sql({"/truncate.sql"})
    void insertHotPlaceArticle() {
        // given
        HotPlaceArticle hotPlaceArticle = HotPlaceArticle.builder()
            .hotPlaceId("1")
            .userId("1")
            .hotPlaceName("서울")
            .imageUrl("www.naver.com")
            .content("content")
            .build();

        // when
        Long articleId = hotPlaceArticleRepository.insertHotPlaceArticle(hotPlaceArticle);

        // then
        assertThat(articleId).isEqualTo(1);
    }
}
