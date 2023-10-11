package com.ssafy.enjoytrip.integration.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.ssafy.enjoytrip.core.hotplace.model.dao.HotPlaceRepository;
import com.ssafy.enjoytrip.core.hotplace.model.dto.request.HotPlaceArticleSaveRequest;
import com.ssafy.enjoytrip.core.hotplace.model.dto.request.HotPlaceSaveRequest;
import com.ssafy.enjoytrip.core.hotplace.model.dto.request.HotPlaceSearchRequest;
import com.ssafy.enjoytrip.core.hotplace.model.dto.response.HotPlaceDetailResponse;
import com.ssafy.enjoytrip.core.hotplace.model.dto.response.HotPlaceResponse;
import com.ssafy.enjoytrip.core.hotplace.service.HotPlaceService;
import com.ssafy.enjoytrip.global.error.HotPlaceException;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@DisplayName("HotPlaceService 통합 테스트")
class HotPlaceServiceTest {

    @Autowired
    private HotPlaceService hotPlaceService;
    @Autowired
    private HotPlaceRepository hotPlaceRepository;

    @Test
    @DisplayName("HotPlace를 정상적으로 생성한다.")
    void HotPlaceServiceSuccess() {
        // given
        HotPlaceSaveRequest hotPlaceSaveRequest = HotPlaceSaveRequest.builder()
            .hotPlaceId("1")
            .hotPlaceName("test")
            .roadAddressName("test")
            .addressName("test")
            .placeUrl("abc")
            .x(1.0)
            .y(1.0)
            .build();
        // when
        String hotPlaceId = hotPlaceService.insertHotPlace(hotPlaceSaveRequest);

        // then
        assertThat(hotPlaceId).isEqualTo("1");
    }

    @Test
    @DisplayName("HotPlace Article을 정상적으로 생성한다.")
    @Sql({"/truncate.sql", "/user.sql", "/hotplace.sql"})
    void HotPlaceArticleServiceSuccess() {
        // given
        String hotPlaceId = "1";
        String user = "test";
        List<String> tags = List.of("test", "test2");
        HotPlaceArticleSaveRequest hotPlaceArticle = HotPlaceArticleSaveRequest.builder()
            .content("test")
            .imageUrl("test")
            .hotPlaceName("test")
            .tagName(tags)
            .build();

        // when
        Long articleId = hotPlaceService.insertHotPlaceArticle(hotPlaceId, hotPlaceArticle, user);

        // then
        assertThat(articleId).isEqualTo(1L);
    }


    @Test
    @DisplayName("hotPlaceId가 존재하지 않는 경우 HotPlaceArticle을 생성할 수 없다.")
    @Sql({"/truncate.sql", "/user.sql"})
    void hotPlaceArticleFailWithDifferentId() {
        String wrongHotPlaceId = "2";
        String user = "test";
        List<String> tags = List.of("test", "test2");
        HotPlaceArticleSaveRequest hotPlaceArticle = HotPlaceArticleSaveRequest.builder()
            .content("test")
            .imageUrl("test")
            .hotPlaceName("test")
            .tagName(tags)
            .build();

        // when & then
        assertThatCode(
            () -> hotPlaceService.insertHotPlaceArticle(wrongHotPlaceId, hotPlaceArticle, user))
            .isInstanceOf(HotPlaceException.class)
            .hasMessage("존재하지 않는 핫플레이스입니다.");
    }

    @Test
    @DisplayName("유저가 존재하지 않는 경우 HotPlaceArticle을 생성할 수 없다.")
    @Sql({"/truncate.sql", "/hotplace.sql"})
    void hotPlaceArticleFailWithDifferentUser() {
        String hotPlaceId = "1";
        String wrongUser = "wrongUser";
        List<String> tags = List.of("test", "test2");
        HotPlaceArticleSaveRequest hotPlaceArticle = HotPlaceArticleSaveRequest.builder()
            .content("test")
            .imageUrl("test")
            .hotPlaceName("test")
            .tagName(tags)
            .build();

        // when & then
        assertThatCode(
            () -> hotPlaceService.insertHotPlaceArticle(hotPlaceId, hotPlaceArticle, wrongUser))
            .isInstanceOf(HotPlaceException.class)
            .hasMessage("존재하지 않는 유저입니다.");
    }

    @Test
    @DisplayName("HotPlace 목록을 정상적으로 조회한다.")
    @Sql({"/truncate.sql", "/user.sql", "/hotplace.sql"})
    void getHotPlaceListSuccess() {
        // given
        HotPlaceSearchRequest hotPlaceSearchRequest = HotPlaceSearchRequest.builder()
            .keyword("Example")
            .build();
        // when
        List<HotPlaceResponse> hotPlaceResponses = hotPlaceService.selectAllHotPlace(
            hotPlaceSearchRequest);

        // then
        assertThat(hotPlaceResponses.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("HotPlace 상세 정보를 정상적으로 조회한다.")
    @Sql({"/truncate.sql", "/user.sql", "/hotplace.sql", "/hotplaceArticle.sql"})
    void getHotPlaceDetailSuccess() {
        // given
        String hotPlaceId = "1";
        // when
        HotPlaceDetailResponse hotPlaceResponse = hotPlaceService.selectAllByHotPlaceId(hotPlaceId);

        // then
        assertAll(
            () -> assertThat(hotPlaceResponse).extracting("hotPlaceId").isEqualTo("1"),
            () -> assertThat(hotPlaceResponse).extracting("hotPlaceName")
                .isEqualTo("Example Place"),
            () -> assertThat(hotPlaceResponse.getHotPlaceArticles().size()).isEqualTo(1),
            () -> assertThat(hotPlaceResponse.getHotPlaceTagResponses().size()).isEqualTo(1)
        );
    }

}
