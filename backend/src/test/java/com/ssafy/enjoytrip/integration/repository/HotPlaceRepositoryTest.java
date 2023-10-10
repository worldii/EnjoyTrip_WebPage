package com.ssafy.enjoytrip.integration.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

import com.github.pagehelper.Page;
import com.ssafy.enjoytrip.core.hotplace.model.dao.HotPlaceRepository;
import com.ssafy.enjoytrip.core.hotplace.model.entity.HotPlace;
import com.ssafy.enjoytrip.core.hotplace.model.entity.HotPlaceArticle;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@DisplayName("HotPlaceRepositoryTest 통합 테스트")
@SpringBootTest
class HotPlaceRepositoryTest {

    @Autowired
    private HotPlaceRepository hotPlaceRepository;

    @Test
    @DisplayName("HotPlace 를 생성 한다")
    @Sql({"/truncate.sql"})
    void createHotPlaceTest() {
        // given
        HotPlace hotPlace = HotPlace.builder()
            .hotPlaceId("1")
            .hotPlaceName("서울")
            .placeUrl("www.naver.com")
            .x(1.0)
            .y(1.0)
            .roadAddressName("서울")
            .addressName("서울")
            .build();

        // when & then
        assertThatCode(() -> hotPlaceRepository.insertHotPlace(hotPlace))
            .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("HotPlace Id를 바탕으로, HotPlace를 조회한다.")
    @Sql({"/truncate.sql", "/hotPlace.sql"})
    void selectAllByHotPlaceIdTest() {
        // given
        String hotPlaceId = "1";

        // when
        Optional<HotPlace> hotPlace = hotPlaceRepository.selectAllByHotPlaceId(hotPlaceId);

        // then
        assertThat(hotPlace).isNotNull();
    }

    @Test
    @DisplayName("HotPlace 모두를 조회한다.")
    @Sql({"/truncate.sql", "/hotPlace.sql"})
    void selectAllHotPlaceTest() {
        // given
        String keyword = "서울";

        // when
        Page<HotPlace> hotPlaces = hotPlaceRepository.selectAllHotPlace(keyword);

        // then
        assertThat(hotPlaces).isNotNull();
    }


    @Test
    @DisplayName("HotPlaceArticle를 조회한다.")
    @Sql({"/truncate.sql", "/hotPlaceArticle.sql"})
    void selectHotPlaceArticleByArticleId() {
        // given
        Long hotPlaceArticleId = 1L;

        // when
        Optional<HotPlaceArticle> hotPlace = hotPlaceRepository.selectHotPlaceArticleByArticleId(
            hotPlaceArticleId);

        // then
        assertThat(hotPlace).isNotNull();
    }

    @Test
    @DisplayName("HotPlace Vote Count 를 업데이트 한다.")
    @Sql({"/truncate.sql"})
    void increaseVoteCount() {
        // given
        HotPlace hotPlace = HotPlace.builder()
            .hotPlaceId("1")
            .hotPlaceName("서울")
            .placeUrl("www.naver.com")
            .x(1.0)
            .y(1.0)
            .roadAddressName("서울")
            .addressName("서울").build();

        hotPlaceRepository.insertHotPlace(hotPlace);

        // when
        hotPlace.updateVoteCount(3L);
        hotPlaceRepository.updateHotPlace(hotPlace);

        // then
        assertThat(hotPlace.getVote()).isEqualTo(3L);
    }
}
