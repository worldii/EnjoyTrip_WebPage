package com.ssafy.enjoytrip.acceptance;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.http.HttpStatus.OK;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

@DisplayName("미디어 관련 기능")
class MediaAcceptanceTest extends AcceptanceTest {

    @Test
    @DisplayName("파일을 정상적으로 조회한다")
    @Sql({"/truncate.sql", "/fileInfo.sql"})
    void selectFileTest() {
        // given
        // when
        ExtractableResponse<Response> response = RestAssured
            .given()
            .log().all()
            .when()
            .get("/media/1")
            .then()
            .log().all()
            .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(OK.value());
    }
}
