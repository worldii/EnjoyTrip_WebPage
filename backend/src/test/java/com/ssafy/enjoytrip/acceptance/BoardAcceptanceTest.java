package com.ssafy.enjoytrip.acceptance;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.enjoytrip.config.UploadConfig;
import com.ssafy.enjoytrip.core.board.model.dto.request.BoardSaveRequest;
import com.ssafy.enjoytrip.core.board.model.entity.BoardType;
import com.ssafy.enjoytrip.core.user.model.dto.request.UserAddRequest;
import com.ssafy.enjoytrip.core.user.model.dto.request.UserLoginRequest;
import com.ssafy.enjoytrip.global.auth.model.dto.response.TokenResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

@DisplayName("게시판 관련 기능")
@Import(UploadConfig.class)
class BoardAcceptanceTest extends AcceptanceTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("게시판을 정상적으로 등록한다")
    void insertBoardTest() throws JsonProcessingException {
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
            .body(userAddRequest)
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
        String json = objectMapper.writeValueAsString(boardSaveRequest);
        List<MultipartFile> imagesFiles = List.of(
            new MockMultipartFile("image1", "image1.jpg", "image/jpeg", "image1".getBytes()),
            new MockMultipartFile("image2", "image2.jpg", "image/jpeg", "image2".getBytes()),
            new MockMultipartFile("image3", "image3.jpg", "image/jpeg", "image3".getBytes())
        );
        
        // TODO : FILE 테스트 ....
        ExtractableResponse<Response> response = RestAssured
            .given()
            .header("Authorization", accessToken)
            .multiPart("json", json)
            .contentType(MULTIPART_FORM_DATA_VALUE)
            .log().all()
            .when()
            .post("/board")
            .then()
            .log().all()
            .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(CREATED.value());
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
