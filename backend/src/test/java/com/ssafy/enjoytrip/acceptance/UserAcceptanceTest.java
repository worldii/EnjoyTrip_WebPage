package com.ssafy.enjoytrip.acceptance;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.ssafy.enjoytrip.user.model.dto.request.UserAddRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("User 인수 테스트")
public class UserAcceptanceTest extends AcceptanceTest {

    @Test
    @DisplayName("유저가 정상적으로 회원 가입한다.")
    void 유저_회원가입_성공() {
        // given
        UserAddRequest userAddRequest = UserAddRequest.builder()
            .userId("jongha")
            .name("jongha")
            .address("test")
            .password("test")
            .email("test")
            .authority(1)
            .salt("test")
            .build();

        // when
        ExtractableResponse<Response> response = RestAssured
            .given()
            .contentType(APPLICATION_JSON_VALUE)
            .body(userAddRequest)
            .log().all()
            .when()
            .post("/user")
            .then()
            .log().all()
            .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(OK.value());
    }
}
