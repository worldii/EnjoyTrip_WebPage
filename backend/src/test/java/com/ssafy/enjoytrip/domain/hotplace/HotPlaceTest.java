package com.ssafy.enjoytrip.domain.hotplace;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

import com.ssafy.enjoytrip.core.hotplace.model.entity.HotPlace;
import com.ssafy.enjoytrip.global.error.HotPlaceException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

@DisplayName("HotPlace 테스트")
class HotPlaceTest {

    @Test
    @DisplayName("HotPlace 를 정상적으로 생성 한다")
    void createHotPlaceSuccess() {
        // given
        String hotPlaceId = "1";
        String hotPlaceName = "서울";
        String addressName = "서울";
        String placeUrl = "www.naver.com";
        Double x = 1.0;
        Double y = 1.0;
        String roadAddressName = "서울";

        // when & then
        assertThatCode(
                        () ->
                                HotPlace.builder()
                                        .hotPlaceId(hotPlaceId)
                                        .hotPlaceName(hotPlaceName)
                                        .addressName(addressName)
                                        .imageUrl(placeUrl)
                                        .x(x)
                                        .y(y)
                                        .roadAddressName(roadAddressName)
                                        .build())
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("HotPlaceId가 null일 경우, 예외가 발생한다")
    void createHotPlaceFailByHotPlaceId(String hotPlaceId) {
        // given
        String hotPlaceName = "서울";
        String addressName = "서울";
        String placeUrl = "www.naver.com";
        Double x = 1.0;
        Double y = 1.0;
        String roadAddressName = "서울";

        // when & then
        assertThatCode(
                        () ->
                                HotPlace.builder()
                                        .hotPlaceId(hotPlaceId)
                                        .hotPlaceName(hotPlaceName)
                                        .addressName(addressName)
                                        .imageUrl(placeUrl)
                                        .x(x)
                                        .y(y)
                                        .roadAddressName(roadAddressName)
                                        .build())
                .isInstanceOf(HotPlaceException.class)
                .hasMessage("핫플레이스 아이디가 없습니다.");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("HotPlaceName이 null일 경우, 예외가 발생한다")
    void createHotPlaceFailByHotPlaceName(String hotPlaceName) {
        // given
        String hotPlaceId = "1";
        String addressName = "서울";
        String placeUrl = "www.naver.com";
        Double x = 1.0;
        Double y = 1.0;
        String roadAddressName = "서울";

        // when & then
        assertThatCode(
                        () ->
                                HotPlace.builder()
                                        .hotPlaceId(hotPlaceId)
                                        .hotPlaceName(hotPlaceName)
                                        .addressName(addressName)
                                        .imageUrl(placeUrl)
                                        .x(x)
                                        .y(y)
                                        .roadAddressName(roadAddressName)
                                        .build())
                .isInstanceOf(HotPlaceException.class)
                .hasMessage("핫플레이스 이름이 없습니다.");
    }

    @Test
    @DisplayName("HotPlace 좌표의 X가 null일 경우, 예외가 발생한다")
    void createHotPlaceFailByX() {
        // given
        String hotPlaceId = "1";
        String hotPlaceName = "서울";
        String addressName = "서울";
        String placeUrl = "www.naver.com";
        Double x = null;
        Double y = 1.0;
        String roadAddressName = "서울";

        // when & then
        assertThatCode(
                        () ->
                                HotPlace.builder()
                                        .hotPlaceId(hotPlaceId)
                                        .hotPlaceName(hotPlaceName)
                                        .addressName(addressName)
                                        .imageUrl(placeUrl)
                                        .x(x)
                                        .y(y)
                                        .roadAddressName(roadAddressName)
                                        .build())
                .isInstanceOf(HotPlaceException.class)
                .hasMessage("x좌표가 없습니다.");
    }

    @Test
    @DisplayName("HotPlace 좌표의 Y가 null일 경우, 예외가 발생한다")
    void createHotPlaceFailByY() {
        // given
        String hotPlaceId = "1";
        String hotPlaceName = "서울";
        String addressName = "서울";
        String placeUrl = "www.naver.com";
        Double x = 1.0;
        Double y = null;
        String roadAddressName = "서울";

        // when & then
        assertThatCode(
                        () ->
                                HotPlace.builder()
                                        .hotPlaceId(hotPlaceId)
                                        .hotPlaceName(hotPlaceName)
                                        .addressName(addressName)
                                        .imageUrl(placeUrl)
                                        .x(x)
                                        .y(y)
                                        .roadAddressName(roadAddressName)
                                        .build())
                .isInstanceOf(HotPlaceException.class)
                .hasMessage("y좌표가 없습니다.");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("HotPlace 주소가 null일 경우, 예외가 발생한다")
    void createHotPlaceFailByAddressName(String addressName) {
        // given
        String hotPlaceId = "1";
        String hotPlaceName = "서울";
        String placeUrl = "www.naver.com";
        Double x = 1.0;
        Double y = 1.0;
        String roadAddressName = "서울";

        // when & then
        assertThatCode(
                        () ->
                                HotPlace.builder()
                                        .hotPlaceId(hotPlaceId)
                                        .hotPlaceName(hotPlaceName)
                                        .addressName(addressName)
                                        .imageUrl(placeUrl)
                                        .x(x)
                                        .y(y)
                                        .roadAddressName(roadAddressName)
                                        .build())
                .isInstanceOf(HotPlaceException.class)
                .hasMessage("주소가 없습니다.");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("HotPlace 도로명 주소가 null일 경우, 예외가 발생한다")
    void createHotPlaceFailByRoadAddressName(String roadAddressName) {
        // given
        String hotPlaceId = "1";
        String hotPlaceName = "서울";
        String addressName = "서울";
        String placeUrl = "www.naver.com";
        Double x = 1.0;
        Double y = 1.0;

        // when & then
        assertThatCode(
                        () ->
                                HotPlace.builder()
                                        .hotPlaceId(hotPlaceId)
                                        .hotPlaceName(hotPlaceName)
                                        .addressName(addressName)
                                        .imageUrl(placeUrl)
                                        .x(x)
                                        .y(y)
                                        .roadAddressName(roadAddressName)
                                        .build())
                .isInstanceOf(HotPlaceException.class)
                .hasMessage("도로명 주소가 없습니다.");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("HotPlace URL이 null일 경우, 예외가 발생한다")
    void createHotPlaceFailByPlaceUrl(String placeUrl) {
        // given
        String hotPlaceId = "1";
        String hotPlaceName = "서울";
        String addressName = "서울";
        Double x = 1.0;
        Double y = 1.0;
        String roadAddressName = "서울";

        // when & then
        assertThatCode(
                        () ->
                                HotPlace.builder()
                                        .hotPlaceId(hotPlaceId)
                                        .hotPlaceName(hotPlaceName)
                                        .addressName(addressName)
                                        .imageUrl(placeUrl)
                                        .x(x)
                                        .y(y)
                                        .roadAddressName(roadAddressName)
                                        .build())
                .isInstanceOf(HotPlaceException.class)
                .hasMessage("핫플레이스 URL이 없습니다.");
    }
}
