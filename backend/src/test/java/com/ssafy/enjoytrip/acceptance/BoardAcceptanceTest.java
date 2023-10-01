package com.ssafy.enjoytrip.acceptance;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.ssafy.enjoytrip.core.board.model.dto.request.BoardSaveRequest;
import com.ssafy.enjoytrip.core.board.model.entity.BoardType;
import com.ssafy.enjoytrip.core.user.model.dto.request.UserAddRequest;
import com.ssafy.enjoytrip.core.user.model.dto.request.UserLoginRequest;
import com.ssafy.enjoytrip.global.auth.model.dto.response.TokenResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("게시판 관련 기능")
class BoardAcceptanceTest extends AcceptanceTest {

    @Test
    @DisplayName("게시판을 정상적으로 등록한다")
    void insertBoardTest() {
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
        BoardSaveRequest boardSaveRequest = BoardSaveRequest.builder()
            .boardType(BoardType.NOTICE)
            .content("test")
            .subject("test")
            .build();

        ExtractableResponse<Response> response = RestAssured
            .given()
            .header("Authorization", accessToken)
            .body(boardSaveRequest)
            .contentType(APPLICATION_JSON_VALUE)
            .log().all()
            .when()
            .post("/board")
            .then()
            .log().all()
            .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(OK.value());
    }

    @Test
    @DisplayName("게시판 상세보기를 정상적으로 조회한다")
    void selectBoardTest() {
        // given
        // when
        ExtractableResponse<Response> response = RestAssured
            .given()
            .log().all()
            .when()
            .get("/board/1")
            .then()
            .log().all()
            .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(OK.value());
    }
}
