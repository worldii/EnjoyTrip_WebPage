package com.ssafy.enjoytrip.acceptance;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.ssafy.enjoytrip.board.model.dto.request.CommentModifyRequest;
import com.ssafy.enjoytrip.board.model.dto.request.CommentSaveRequest;
import com.ssafy.enjoytrip.global.auth.model.dto.response.TokenResponse;
import com.ssafy.enjoytrip.user.model.dto.request.UserAddRequest;
import com.ssafy.enjoytrip.user.model.dto.request.UserLoginRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

@DisplayName("댓글 관련 기능 인수 테스트")
@Sql({"/truncate.sql", "/board.sql"})
class CommentAcceptanceTest extends AcceptanceTest {

    @Test
    @DisplayName("댓글을 작성한다.")
    void createComment() {
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
        CommentSaveRequest commentSaveRequest = CommentSaveRequest.builder()
            .content("test")
            .build();

        ExtractableResponse<Response> response = RestAssured
            .given()
            .header("Authorization", accessToken)
            .contentType(APPLICATION_JSON_VALUE)
            .body(commentSaveRequest)
            .log().all()
            .when()
            .post("/comment/1")
            .then()
            .log().all()
            .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(CREATED.value());
    }

    @Test
    @DisplayName("댓글을 조회한다.")
    void selectComment() {
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

        CommentSaveRequest commentSaveRequest = CommentSaveRequest.builder()
            .content("test")
            .build();
        RestAssured
            .given()
            .header("Authorization", accessToken)
            .contentType(APPLICATION_JSON_VALUE)
            .body(commentSaveRequest)
            .log().all()
            .when()
            .post("/comment/1")
            .then()
            .log().all()
            .extract();

        // when
        ExtractableResponse<Response> response = RestAssured
            .given()
            .contentType(APPLICATION_JSON_VALUE)
            .log().all()
            .when()
            .get("/comment/1")
            .then()
            .log().all()
            .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(OK.value());

    }

    @Test
    @DisplayName("댓글을 수정한다.")
    void modifyComment() {
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

        CommentSaveRequest commentSaveRequest = CommentSaveRequest.builder()
            .content("test")
            .build();
        Long boardId = 1L;
        Long commentId = RestAssured
            .given()
            .header("Authorization", accessToken)
            .contentType(APPLICATION_JSON_VALUE)
            .body(commentSaveRequest)
            .log().all()
            .when()
            .post("/comment/{boardId}", boardId)
            .then()
            .log().all()
            .extract().body().as(Long.class);

        // when
        CommentModifyRequest commentModifyRequest = CommentModifyRequest
            .builder()
            .boardId(boardId)
            .content("test2")
            .build();

        ExtractableResponse<Response> response = RestAssured
            .given()
            .header("Authorization", accessToken)
            .contentType(APPLICATION_JSON_VALUE)
            .body(commentModifyRequest)
            .log().all()
            .when()
            .put("/comment/{commentId}", commentId)
            .then()
            .log().all()
            .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(OK.value());
    }


    @Test
    @DisplayName("댓글을 삭제한다.")
    void deleteComment() {
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

        CommentSaveRequest commentSaveRequest = CommentSaveRequest.builder()
            .content("test")
            .build();
        Long boardId = 1L;
        Long commentId = RestAssured
            .given()
            .header("Authorization", accessToken)
            .contentType(APPLICATION_JSON_VALUE)
            .body(commentSaveRequest)
            .log().all()
            .when()
            .post("/comment/{boardId}", boardId)
            .then()
            .log().all()
            .extract().body().as(Long.class);

        // when
        ExtractableResponse<Response> response = RestAssured
            .given()
            .header("Authorization", accessToken)
            .contentType(APPLICATION_JSON_VALUE)
            .log().all()
            .when()
            .delete("/comment/{commentId}", commentId)
            .then()
            .log().all()
            .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(NO_CONTENT.value());
    }

}
