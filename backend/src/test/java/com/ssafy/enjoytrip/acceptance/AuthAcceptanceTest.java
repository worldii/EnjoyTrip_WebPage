package com.ssafy.enjoytrip.acceptance;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.ssafy.enjoytrip.global.auth.model.dto.TokenResponse;
import com.ssafy.enjoytrip.user.model.dto.request.UserAddRequest;
import com.ssafy.enjoytrip.user.model.dto.request.UserLoginRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Auth 관련 기능")
class AuthAcceptanceTest extends AcceptanceTest {

    @Test
    @DisplayName("RefreshToken을 재발급한다.")
    void createRefreshToken() {
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

        // when
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
            .header("Authorization", accessToken)
            .log().all()
            .when()
            .post("/auth/refresh-token")
            .then()
            .log().all()
            .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(OK.value());
    }

    @Test
    @DisplayName("AccessToken을 재발급한다.")
    void createAccessToken() {
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

        // when
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
            .header("Authorization", accessToken)
            .log().all()
            .when()
            .post("/auth/access-token")
            .then()
            .log().all()
            .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(OK.value());
    }
}
