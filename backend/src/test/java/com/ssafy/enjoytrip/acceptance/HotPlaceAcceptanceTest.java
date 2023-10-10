package com.ssafy.enjoytrip.acceptance;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.ssafy.enjoytrip.core.hotplace.model.dto.request.HotPlaceSaveRequest;
import com.ssafy.enjoytrip.core.user.model.dto.request.UserAddRequest;
import com.ssafy.enjoytrip.core.user.model.dto.request.UserLoginRequest;
import com.ssafy.enjoytrip.global.auth.model.dto.response.TokenResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
}
