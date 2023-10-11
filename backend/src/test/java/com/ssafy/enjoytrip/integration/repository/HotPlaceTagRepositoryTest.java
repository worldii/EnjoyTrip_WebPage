package com.ssafy.enjoytrip.integration.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.ssafy.enjoytrip.core.hotplace.model.dao.HotPlaceTagRepository;
import com.ssafy.enjoytrip.core.hotplace.model.entity.HotPlaceTag;
import com.ssafy.enjoytrip.global.error.HotPlaceException;
import java.util.List;
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
    @DisplayName("HotPlaceTag를 id를 가지고 조회한다")
    @Sql({"/truncate.sql", "/hotPlaceTag.sql"})
    void findById() {
        // given
        Long hotPlaceTagId = 1L;

        // when & then
        HotPlaceTag hotPlaceTag = hotPlaceTagRepository.findById(hotPlaceTagId)
            .orElseThrow(() -> new HotPlaceException("해당 태그가 없습니다."));

        // then
        assertThat(hotPlaceTag).isNotNull();
    }

    @Test
    @DisplayName("HotPlaceTag들을 추가한다.")
    @Sql({"/truncate.sql"})
    void insertHotPlaceTags() {
        // given
        String tagName = "태그1";
        String hotPlaceId = "1";
        Long hotPlaceTagId = 1L;
        HotPlaceTag hotPlaceTag = HotPlaceTag.builder()
            .hotPlaceTagId(hotPlaceTagId)
            .tagName(tagName)
            .hotPlaceId(hotPlaceId)
            .build();

        // when
        hotPlaceTagRepository.insertTags(List.of(hotPlaceTag));

        // then
        HotPlaceTag newHotPlaceTag = hotPlaceTagRepository.findById(hotPlaceTagId)
            .orElseThrow(() -> new HotPlaceException("해당 태그가 없습니다."));
        assertThat(newHotPlaceTag).extracting("tagName").isEqualTo(tagName);
    }
}
