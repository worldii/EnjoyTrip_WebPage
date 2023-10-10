package com.ssafy.enjoytrip.integration.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.ssafy.enjoytrip.core.hotplace.model.dao.HotPlaceTagRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@DisplayName("HotPlaceTagRepository 테스트")
class HotPlaceTagRepositoryTest {

    @Autowired
    private HotPlaceTagRepository hotPlaceTagRepository;

    @Test
    @DisplayName("HotPlaceTag들을 조회한다.")
    @Sql({"/truncate.sql", "/hotPlaceTag.sql"})
    void selectHotPlaceTagIdByTagName() {
        // given
        String tagName = "태그1";
        String hotPlaceId = "1";

        // when
        Long hotPlaceTagId = hotPlaceTagRepository
            .selectHotPlaceTagIdByTagNameAndHotPlaceId(tagName, hotPlaceId);

        // then
        assertThat(hotPlaceTagId).isNotNull();
    }
}
