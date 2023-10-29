package com.ssafy.enjoytrip.domain.hotplace;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

import com.ssafy.enjoytrip.core.hotplace.model.entity.HotPlaceArticle;
import com.ssafy.enjoytrip.global.error.HotPlaceException;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

@DisplayName("HotPlaceArticle 테스트")
class HotPlaceArticleTest {

    @Test
    @DisplayName("HotPlaceArticle 를 정상적으로 생성 한다")
    void createHotPlaceArticleSuccess() {
        // given
        Long hotPlaceArticleId = 1L;
        String hotPlaceId = "1";
        String userId = "1";
        String content = "content";

        // when & then
        assertThatCode(
                        () ->
                                HotPlaceArticle.builder()
                                        .hotPlaceArticleId(hotPlaceArticleId)
                                        .hotPlaceId(hotPlaceId)
                                        .userId(userId)
                                        .content(content)
                                        .imageUrl(List.of("www.naver.com"))
                                        .build())
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("HotPlaceId가 null일 경우, 예외가 발생한다")
    void createHotPlaceArticleFailByHotPlaceId(String hotPlaceId) {
        // given
        Long hotPlaceArticleId = 1L;
        String userId = "1";
        String content = "content";

        // when & then
        assertThatCode(
                        () ->
                                HotPlaceArticle.builder()
                                        .hotPlaceArticleId(hotPlaceArticleId)
                                        .hotPlaceId(hotPlaceId)
                                        .userId(userId)
                                        .content(content)
                                        .imageUrl(List.of("www.naver.com"))
                                        .build())
                .isInstanceOf(HotPlaceException.class)
                .hasMessage("핫플레이스 아이디가 없습니다.");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("UserId가 null일 경우, 예외가 발생한다")
    void createHotPlaceArticleFailByUserId(String userId) {
        // given
        Long hotPlaceArticleId = 1L;
        String hotPlaceId = "1";
        String content = "content";

        // when & then
        assertThatCode(
                        () ->
                                HotPlaceArticle.builder()
                                        .hotPlaceArticleId(hotPlaceArticleId)
                                        .hotPlaceId(hotPlaceId)
                                        .userId(userId)
                                        .content(content)
                                        .imageUrl(List.of("www.naver.com"))
                                        .build())
                .isInstanceOf(HotPlaceException.class)
                .hasMessage("유저 아이디가 없습니다.");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("Content가 null일 경우, 예외가 발생한다")
    void createHotPlaceArticleFailByContent(String content) {
        // given
        Long hotPlaceArticleId = 1L;
        String hotPlaceId = "1";
        String userId = "1";

        // when & then
        assertThatCode(
                        () ->
                                HotPlaceArticle.builder()
                                        .hotPlaceArticleId(hotPlaceArticleId)
                                        .hotPlaceId(hotPlaceId)
                                        .userId(userId)
                                        .content(content)
                                        .imageUrl(List.of("www.naver.com"))
                                        .build())
                .isInstanceOf(HotPlaceException.class)
                .hasMessage("내용이 없습니다.");
    }
}
