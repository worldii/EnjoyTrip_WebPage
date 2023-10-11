package com.ssafy.enjoytrip.acceptance;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.ssafy.enjoytrip.core.hotplace.model.dto.request.HotPlaceArticleSaveRequest;
import com.ssafy.enjoytrip.core.hotplace.model.dto.request.HotPlaceSaveRequest;
import com.ssafy.enjoytrip.core.hotplace.model.dto.response.HotPlaceArticleResponse;
import com.ssafy.enjoytrip.core.hotplace.model.dto.response.HotPlaceDetailResponse;
import com.ssafy.enjoytrip.core.hotplace.model.dto.response.HotPlaceResponse;
import com.ssafy.enjoytrip.core.user.model.dto.request.UserAddRequest;
import com.ssafy.enjoytrip.core.user.model.dto.request.UserLoginRequest;
import com.ssafy.enjoytrip.global.auth.model.dto.response.TokenResponse;
import com.ssafy.enjoytrip.global.error.PageInfoRequest;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

@DisplayName("HotPlace 인수테스트")
class HotPlaceAcceptanceTest extends AcceptanceTest {

    @Test
    @DisplayName("HotPlace를 생성한다.")
    void createHotPlace() {
        // given
        UserAddRequest userAddRequest = UserAddRequest.builder()
            .userId("jongha")
            .name("jongha")
            .address("test")
            .password("test")
            .email("test")
            .authority(1)
            .build();

        RestAssured
            .given()
            .contentType(APPLICATION_JSON_VALUE)
            .body(userAddRequest)
            .log().all()
            .when()
            .post("/user")
            .then()
            .log().all()
            .extract();

        UserLoginRequest userLoginRequest = UserLoginRequest.builder()
            .userId("jongha")
            .password("test")
            .build();

        String accessToken = RestAssured
            .given()
            .contentType(APPLICATION_JSON_VALUE)
            .body(userLoginRequest)
            .log().all()
            .when()
            .post("/user/login")
            .then()
            .log().all()
            .extract()
            .as(TokenResponse.class)
            .getAccessToken();

        // when
        HotPlaceSaveRequest hotPlaceSaveRequest = HotPlaceSaveRequest.builder()
            .hotPlaceId("test")
            .hotPlaceName("test")
            .x(1.0)
            .y(1.0)
            .placeUrl("test")
            .addressName("test")
            .roadAddressName("test")
            .build();

        ExtractableResponse<Response> response = RestAssured
            .given()
            .header("Authorization", accessToken)
            .body(hotPlaceSaveRequest)
            .contentType(APPLICATION_JSON_VALUE)
            .log().all()
            .when()
            .post("/hotplace")
            .then()
            .log().all()
            .extract();
        // then
        assertThat(response.statusCode()).isEqualTo(CREATED.value());
    }

    @Test
    @DisplayName("HotPlace Article을  생성한다.")
    @Sql({"/truncate.sql"})
    void selectHotPlace() {
        // given
        UserAddRequest userAddRequest = UserAddRequest.builder()
            .userId("jongha")
            .name("jongha")
            .address("test")
            .password("test")
            .email("test")
            .authority(1)
            .build();

        RestAssured
            .given()
            .contentType(APPLICATION_JSON_VALUE)
            .body(userAddRequest)
            .log().all()
            .when()
            .post("/user")
            .then()
            .log().all()
            .extract();

        UserLoginRequest userLoginRequest = UserLoginRequest.builder()
            .userId("jongha")
            .password("test")
            .build();

        String accessToken = RestAssured
            .given()
            .contentType(APPLICATION_JSON_VALUE)
            .body(userLoginRequest)
            .log().all()
            .when()
            .post("/user/login")
            .then()
            .log().all()
            .extract()
            .as(TokenResponse.class)
            .getAccessToken();

        // when
        HotPlaceSaveRequest hotPlaceSaveRequest = HotPlaceSaveRequest.builder()
            .hotPlaceId("test")
            .hotPlaceName("test")
            .x(1.0)
            .y(1.0)
            .placeUrl("test")
            .addressName("test")
            .roadAddressName("test")
            .build();

        RestAssured
            .given()
            .body(hotPlaceSaveRequest)
            .header("Authorization", accessToken)
            .contentType(APPLICATION_JSON_VALUE)
            .log().all()
            .when()
            .post("/hotplace")
            .then()
            .log().all()
            .extract();

        HotPlaceArticleSaveRequest hotPlaceArticleSaveRequest = HotPlaceArticleSaveRequest.builder()
            .hotPlaceName("test")
            .imageUrl("test")
            .content("test")
            .build();

        ExtractableResponse<Response> response = RestAssured
            .given()
            .header("Authorization", accessToken)
            .contentType(APPLICATION_JSON_VALUE)
            .body(hotPlaceArticleSaveRequest)
            .log().all()
            .when()
            .post("/hotplace/{hotPlaceName}", "test")
            .then()
            .log().all()
            .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(CREATED.value());
    }

    @Test
    @DisplayName("HotPlace 목록을 조회한다")
    @Sql({"/truncate.sql", "/hotplace.sql"})
    void getHotPlaceListTest() {
        // given
        PageInfoRequest pageInfoRequest = PageInfoRequest.builder()
            .page(1)
            .pageSize(1)
            .build();
        String keyword = "Example";

        // when
        ExtractableResponse<Response> response = RestAssured
            .given()
            .param("page", pageInfoRequest.getPage())
            .param("pageSize", pageInfoRequest.getPageSize())
            .param("keyword", keyword)
            .contentType(APPLICATION_JSON_VALUE)
            .log().all()
            .when()
            .get("/hotplace")
            .then()
            .log().all()
            .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(OK.value());
        assertThat(response.body().as(
            new TypeRef<List<HotPlaceResponse>>() {
            }).size())
            .isEqualTo(2);
    }

    @Test
    @DisplayName("HotPlace detail 목록을 조회한다")
    @Sql({"/truncate.sql", "/hotplace.sql", "/hotplaceArticle.sql"})
    void getHotPlaceDetailTest() {
        // given
        String hotPlaceId = "1";

        // when
        ExtractableResponse<Response> response = RestAssured
            .given()
            .contentType(APPLICATION_JSON_VALUE)
            .log().all()
            .when()
            .get("/hotplace/{hotPlaceId}", hotPlaceId)
            .then()
            .log().all()
            .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(OK.value());
        assertThat(response.body().as(HotPlaceDetailResponse.class))
            .extracting("hotPlaceId")
            .isEqualTo(hotPlaceId);
    }


    @Test
    @DisplayName("HotPlace Article을 조회한다")
    @Sql({"/truncate.sql", "/hotplaceArticle.sql", "/hotplace.sql"})
    void getHotPlaceArticleDetailTest() {
        // given
        String hotPlaceId = "1";
        Long hotPlaceArticleId = 1L;

        // when
        ExtractableResponse<Response> response = RestAssured
            .given()
            .contentType(APPLICATION_JSON_VALUE)
            .log().all()
            .when()
            .get("/hotplace/{hotPlaceId}/article/{hotPlaceArticleId}", hotPlaceId,
                hotPlaceArticleId)
            .then()
            .log().all()
            .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(OK.value());
        assertThat(response.body().as(HotPlaceArticleResponse.class))
            .extracting("hotPlaceArticleId")
            .isEqualTo(hotPlaceArticleId);
    }

    @Test
    @DisplayName("HotPlace의 투표 수를 업데이트 한다")
    @Sql({"/truncate.sql", "/hotplace.sql", "/user.sql"})
    void voteHotPlaceTest() {
        // given
        String hotPlaceId = "1";
        Long voteCount = RestAssured
            .given()
            .contentType(APPLICATION_JSON_VALUE)
            .log().all()
            .when()
            .get("/hotplace/{hotPlaceId}", hotPlaceId)
            .then()
            .log().all()
            .extract().body().as(HotPlaceDetailResponse.class).getVote();

        UserAddRequest userAddRequest = UserAddRequest.builder()
            .userId("jongha")
            .name("jongha")
            .address("test")
            .password("test")
            .email("test")
            .authority(1)
            .build();

        RestAssured
            .given()
            .contentType(APPLICATION_JSON_VALUE)
            .body(userAddRequest)
            .log().all()
            .when()
            .post("/user")
            .then()
            .log().all()
            .extract();

        UserLoginRequest userLoginRequest = UserLoginRequest.builder()
            .userId("jongha")
            .password("test")
            .build();

        String accessToken = RestAssured
            .given()
            .contentType(APPLICATION_JSON_VALUE)
            .body(userLoginRequest)
            .log().all()
            .when()
            .post("/user/login")
            .then()
            .log().all()
            .extract()
            .as(TokenResponse.class)
            .getAccessToken();

        // when
        ExtractableResponse<Response> response = RestAssured
            .given()
            .contentType(APPLICATION_JSON_VALUE)
            .header("Authorization", accessToken)
            .log().all()
            .when()
            .put("/hotplace/{hotPlaceId}/vote", hotPlaceId)
            .then()
            .log().all()
            .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(OK.value());
        assertThat(response.body().as(HotPlaceDetailResponse.class))
            .extracting("vote")
            .isEqualTo(voteCount + 1);
    }
}

