package com.ssafy.enjoytrip.integration.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.github.pagehelper.Page;
import com.ssafy.enjoytrip.core.hotplace.model.dao.HotPlaceRepository;
import com.ssafy.enjoytrip.core.hotplace.model.entity.HotPlace;
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
    @DisplayName("HotPlace Id를 바탕으로, HotPlace를 조회한다.")
    @Sql({"/truncate.sql", "/hotPlace.sql"})
    void findById() {
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
    void findAll() {
        // given
        String keyword = "서울";

        // when
        Page<HotPlace> hotPlaces = hotPlaceRepository.selectAllHotPlace(keyword);

        // then
        assertThat(hotPlaces).isNotNull();
    }
}
