package com.ssafy.enjoytrip.acceptance;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.http.HttpStatus.NO_CONTENT;
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
import java.io.IOException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.jdbc.Sql;

@DisplayName("게시판 이미지 관련 기능")
class BoardImageAcceptanceTest extends AcceptanceTest {

    @Test
    @DisplayName("게시판 이미지 업로드")
    @Sql({"/truncate.sql"})
    void uploadBoardImage() throws IOException {
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

        MockMultipartFile file =
                new MockMultipartFile(
                        "data", "test.png", MediaType.IMAGE_PNG_VALUE, "test".getBytes());
        // when
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

        // when
        ExtractableResponse<Response> response =
                RestAssured.given()
                        .header("Authorization", accessToken)
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                        .multiPart("data", "data", file.getBytes())
                        .log()
                        .all()
                        .when()
                        .post("/board/media/" + boardId)
                        .then()
                        .log()
                        .all()
                        .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(OK.value());
    }

    @Test
    @DisplayName("게시판 이미지 수정")
    @Sql({"/truncate.sql"})
    void modifyBoardImage() throws IOException {
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

        MockMultipartFile file =
                new MockMultipartFile(
                        "data", "test.png", MediaType.IMAGE_PNG_VALUE, "test".getBytes());
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

        RestAssured.given()
                .header("Authorization", accessToken)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .multiPart("data", "data", file.getBytes())
                .log()
                .all()
                .when()
                .post("/board/media/" + boardId)
                .then()
                .log()
                .all()
                .extract();

        MockMultipartFile newFile =
                new MockMultipartFile(
                        "data", "test2.png", MediaType.IMAGE_PNG_VALUE, "test2".getBytes());
        ExtractableResponse<Response> response =
                RestAssured.given()
                        .header("Authorization", accessToken)
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                        .multiPart("data", "data", newFile.getBytes())
                        .log()
                        .all()
                        .when()
                        .put("/board/media/" + boardId)
                        .then()
                        .log()
                        .all()
                        .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(OK.value());
    }

    @Test
    @DisplayName("게시판 이미지 삭제")
    @Sql({"/truncate.sql"})
    void deleteBoardImage() throws IOException {
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

        MockMultipartFile file =
                new MockMultipartFile(
                        "data", "test.png", MediaType.IMAGE_PNG_VALUE, "test".getBytes());
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

        RestAssured.given()
                .header("Authorization", accessToken)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .multiPart("data", "data", file.getBytes())
                .log()
                .all()
                .when()
                .post("/board/media/" + boardId)
                .then()
                .log()
                .all()
                .extract();

        // when
        ExtractableResponse<Response> response =
                RestAssured.given()
                        .header("Authorization", accessToken)
                        .log()
                        .all()
                        .when()
                        .delete("/board/media/" + boardId)
                        .then()
                        .log()
                        .all()
                        .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(NO_CONTENT.value());
    }
}
