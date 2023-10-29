package com.ssafy.enjoytrip.acceptance;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.ssafy.enjoytrip.config.UploadConfig;
import com.ssafy.enjoytrip.core.board.model.dto.request.BoardModifyRequest;
import com.ssafy.enjoytrip.core.board.model.dto.request.BoardSaveRequest;
import com.ssafy.enjoytrip.core.board.model.dto.response.BoardDetailResponse;
import com.ssafy.enjoytrip.core.board.model.entity.BoardType;
import com.ssafy.enjoytrip.core.user.model.dto.request.UserAddRequest;
import com.ssafy.enjoytrip.core.user.model.dto.request.UserLoginRequest;
import com.ssafy.enjoytrip.global.auth.model.dto.response.TokenResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.io.IOException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

@DisplayName("게시판 관련 기능")
@Import(UploadConfig.class)
class BoardAcceptanceTest extends AcceptanceTest {

    @Test
    @DisplayName("게시판을 정상적으로 등록한다")
    void insertBoardTest() {
        // given
        UserAddRequest userAddRequest =
                UserAddRequest.builder()
                        .userId("jongha")
                        .name("jongha")
                        .address("test")
                        .password("test")
                        .email("test")
                        .authority(1)
                        .build();

        RestAssured.given()
                .contentType(APPLICATION_JSON_VALUE)
                .log()
                .all()
                .body(userAddRequest)
                .when()
                .post("/user")
                .then()
                .log()
                .all()
                .extract();

        UserLoginRequest userLoginRequest =
                UserLoginRequest.builder().userId("jongha").password("test").build();

        String accessToken =
                RestAssured.given()
                        .contentType(APPLICATION_JSON_VALUE)
                        .body(userLoginRequest)
                        .log()
                        .all()
                        .when()
                        .post("/user/login")
                        .then()
                        .log()
                        .all()
                        .extract()
                        .as(TokenResponse.class)
                        .getAccessToken();

        // when
        BoardSaveRequest boardSaveRequest =
                BoardSaveRequest.builder()
                        .boardType(BoardType.NOTICE)
                        .content("test")
                        .subject("test")
                        .build();
        ExtractableResponse<Response> response =
                RestAssured.given()
                        .header("Authorization", accessToken)
                        .contentType(APPLICATION_JSON_VALUE)
                        .body(boardSaveRequest)
                        .log()
                        .all()
                        .when()
                        .post("/board")
                        .then()
                        .log()
                        .all()
                        .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(CREATED.value());
    }

    @Test
    @DisplayName("게시판 상세보기를 정상적으로 조회한다")
    void selectBoardTest() {
        // given
        // when
        ExtractableResponse<Response> response =
                RestAssured.given().log().all().when().get("/board/1").then().log().all().extract();

        // then
        assertThat(response.statusCode()).isEqualTo(OK.value());
    }

    @Test
    @DisplayName("게시판을 정상적으로 수정한다")
    void updateBoardTest() throws IOException {
        // given
        UserAddRequest userAddRequest =
                UserAddRequest.builder()
                        .userId("jongha")
                        .name("jongha")
                        .address("test")
                        .password("test")
                        .email("test")
                        .authority(1)
                        .build();

        RestAssured.given()
                .contentType(APPLICATION_JSON_VALUE)
                .log()
                .all()
                .body(userAddRequest)
                .when()
                .post("/user")
                .then()
                .log()
                .all()
                .extract();

        UserLoginRequest userLoginRequest =
                UserLoginRequest.builder().userId("jongha").password("test").build();

        String accessToken =
                RestAssured.given()
                        .contentType(APPLICATION_JSON_VALUE)
                        .body(userLoginRequest)
                        .log()
                        .all()
                        .when()
                        .post("/user/login")
                        .then()
                        .log()
                        .all()
                        .extract()
                        .as(TokenResponse.class)
                        .getAccessToken();
        BoardSaveRequest boardSaveRequest =
                BoardSaveRequest.builder()
                        .boardType(BoardType.NOTICE)
                        .content("test")
                        .subject("test")
                        .build();

        Long boardId =
                RestAssured.given()
                        .header("Authorization", accessToken)
                        .contentType(APPLICATION_JSON_VALUE)
                        .body(boardSaveRequest)
                        .log()
                        .all()
                        .when()
                        .post("/board")
                        .then()
                        .log()
                        .all()
                        .extract()
                        .body()
                        .as(Long.class);

        BoardModifyRequest boardModifyRequest =
                BoardModifyRequest.builder()
                        .boardType(BoardType.NOTICE)
                        .content("test")
                        .subject("test")
                        .build();

        ExtractableResponse<Response> response =
                RestAssured.given()
                        .header("Authorization", accessToken)
                        .contentType(APPLICATION_JSON_VALUE)
                        .body(boardModifyRequest)
                        .log()
                        .all()
                        .when()
                        .put("/board/" + boardId)
                        .then()
                        .log()
                        .all()
                        .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(OK.value());
    }

    @Test
    @DisplayName("게시판의 조회 수를 증가시킨다")
    @Sql({"/truncate.sql", "/board.sql"})
    void updateBoardViewCountTest() {
        // given
        Long boardId = 1L;
        Long hit =
                RestAssured.given()
                        .log()
                        .all()
                        .when()
                        .get("/board/" + boardId)
                        .then()
                        .log()
                        .all()
                        .extract()
                        .body()
                        .as(BoardDetailResponse.class)
                        .getHit();

        // when
        ExtractableResponse<Response> response =
                RestAssured.given()
                        .log()
                        .all()
                        .when()
                        .put("/board/view/" + boardId)
                        .then()
                        .log()
                        .all()
                        .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(OK.value());
        Long newHit =
                RestAssured.given()
                        .log()
                        .all()
                        .when()
                        .get("/board/" + boardId)
                        .then()
                        .log()
                        .all()
                        .extract()
                        .body()
                        .as(BoardDetailResponse.class)
                        .getHit();
        assertThat(newHit).isEqualTo(hit + 1);
    }

    @Test
    @DisplayName("게시판을 정상적으로 삭제한다")
    void deleteBoardTest() {
        // given
        UserAddRequest userAddRequest =
                UserAddRequest.builder()
                        .userId("jongha")
                        .name("jongha")
                        .address("test")
                        .password("test")
                        .email("test")
                        .authority(1)
                        .build();

        RestAssured.given()
                .contentType(APPLICATION_JSON_VALUE)
                .log()
                .all()
                .body(userAddRequest)
                .when()
                .post("/user")
                .then()
                .log()
                .all()
                .extract();

        UserLoginRequest userLoginRequest =
                UserLoginRequest.builder().userId("jongha").password("test").build();

        String accessToken =
                RestAssured.given()
                        .contentType(APPLICATION_JSON_VALUE)
                        .body(userLoginRequest)
                        .log()
                        .all()
                        .when()
                        .post("/user/login")
                        .then()
                        .log()
                        .all()
                        .extract()
                        .as(TokenResponse.class)
                        .getAccessToken();

        BoardSaveRequest boardSaveRequest =
                BoardSaveRequest.builder()
                        .boardType(BoardType.NOTICE)
                        .content("test")
                        .subject("test")
                        .build();

        Long boardId =
                RestAssured.given()
                        .contentType(APPLICATION_JSON_VALUE)
                        .header("Authorization", accessToken)
                        .body(boardSaveRequest)
                        .log()
                        .all()
                        .when()
                        .post("/board")
                        .then()
                        .log()
                        .all()
                        .extract()
                        .body()
                        .as(Long.class);

        // when
        ExtractableResponse<Response> response =
                RestAssured.given()
                        .header("Authorization", accessToken)
                        .log()
                        .all()
                        .when()
                        .delete("/board/" + boardId)
                        .then()
                        .log()
                        .all()
                        .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(NO_CONTENT.value());
    }
}
