package com.ssafy.enjoytrip.acceptance;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.ssafy.enjoytrip.core.plan.model.dto.request.PlanBoardSaveRequest;
import com.ssafy.enjoytrip.core.plan.model.dto.request.PlanSaveRequest;
import com.ssafy.enjoytrip.core.user.model.dto.request.UserAddRequest;
import com.ssafy.enjoytrip.core.user.model.dto.request.UserLoginRequest;
import com.ssafy.enjoytrip.global.auth.model.dto.response.TokenResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.sql.Date;
import java.sql.Time;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

@DisplayName("플랜 관련 기능")
class PlanAcceptanceTest extends AcceptanceTest {

    @Test
    @DisplayName("플랜을 정상적으로 생성한다")
    void createPlan() {
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
                .body(userAddRequest)
                .log()
                .all()
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

        List<PlanSaveRequest> plans =
                List.of(
                        PlanSaveRequest.builder()
                                .planOrder(1)
                                .placeName("test")
                                .content("test")
                                .startTime(new Time(1, 1, 1).toString())
                                .endTime(new Time(1, 1, 2).toString())
                                .expectDuration(1L)
                                .expectDate(new Date(2021, 1, 1).toString())
                                .build(),
                        PlanSaveRequest.builder()
                                .planOrder(2)
                                .placeName("test")
                                .content("test")
                                .startTime(new Time(1, 1, 1).toString())
                                .endTime(new Time(1, 1, 2).toString())
                                .expectDuration(1L)
                                .expectDate(new Date(2021, 1, 1).toString())
                                .build());

        PlanBoardSaveRequest planBoardSaveRequest =
                PlanBoardSaveRequest.builder()
                        .title("test")
                        .startDate("2021-01-01")
                        .endDate("2021-01-02")
                        .planList(plans)
                        .userId("jongha")
                        .title("test")
                        .build();

        // when
        ExtractableResponse<Response> response =
                RestAssured.given()
                        .header("Authorization", accessToken)
                        .body(planBoardSaveRequest)
                        .contentType(APPLICATION_JSON_VALUE)
                        .log()
                        .all()
                        .when()
                        .post("/plan")
                        .then()
                        .log()
                        .all()
                        .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(CREATED.value());
    }

    @Test
    @DisplayName("플랜 보드를 정상적으로 조회한다")
    void selectPlanBoardById() {
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
                .body(userAddRequest)
                .log()
                .all()
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

        List<PlanSaveRequest> plans =
                List.of(
                        PlanSaveRequest.builder()
                                .planOrder(1)
                                .placeName("test")
                                .content("test")
                                .startTime(new Time(1, 1, 1).toString())
                                .endTime(new Time(1, 1, 2).toString())
                                .expectDuration(1L)
                                .expectDate(new Date(2021, 1, 1).toString())
                                .build(),
                        PlanSaveRequest.builder()
                                .planOrder(2)
                                .placeName("test")
                                .content("test")
                                .startTime(new Time(1, 1, 1).toString())
                                .endTime(new Time(1, 1, 2).toString())
                                .expectDuration(1L)
                                .expectDate(new Date(2021, 1, 1).toString())
                                .build());

        PlanBoardSaveRequest planBoardSaveRequest =
                PlanBoardSaveRequest.builder()
                        .title("test")
                        .startDate("2021-01-01")
                        .endDate("2021-01-02")
                        .planList(plans)
                        .userId("jongha")
                        .title("test")
                        .build();

        Long planBoardId =
                RestAssured.given()
                        .header("Authorization", accessToken)
                        .body(planBoardSaveRequest)
                        .contentType(APPLICATION_JSON_VALUE)
                        .log()
                        .all()
                        .when()
                        .post("/plan")
                        .then()
                        .log()
                        .all()
                        .extract()
                        .body()
                        .as(Long.class);

        ExtractableResponse<Response> response =
                RestAssured.given()
                        .header("Authorization", accessToken)
                        .body(planBoardSaveRequest)
                        .contentType(APPLICATION_JSON_VALUE)
                        .log()
                        .all()
                        .when()
                        .get("/plan/" + planBoardId)
                        .then()
                        .log()
                        .all()
                        .extract();
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("플랜 보드를 정상적으로 조회한다")
    void selectAllByUserId() {
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
                .body(userAddRequest)
                .log()
                .all()
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

        List<PlanSaveRequest> plans =
                List.of(
                        PlanSaveRequest.builder()
                                .planOrder(1)
                                .placeName("test")
                                .content("test")
                                .startTime(new Time(1, 1, 1).toString())
                                .endTime(new Time(1, 1, 2).toString())
                                .expectDuration(1L)
                                .expectDate(new Date(2021, 1, 1).toString())
                                .build(),
                        PlanSaveRequest.builder()
                                .planOrder(2)
                                .placeName("test")
                                .content("test")
                                .startTime(new Time(1, 1, 1).toString())
                                .endTime(new Time(1, 1, 2).toString())
                                .expectDuration(1L)
                                .expectDate(new Date(2021, 1, 1).toString())
                                .build());

        PlanBoardSaveRequest planBoardSaveRequest =
                PlanBoardSaveRequest.builder()
                        .title("test")
                        .startDate("2021-01-01")
                        .endDate("2021-01-02")
                        .planList(plans)
                        .userId("jongha")
                        .title("test")
                        .build();

        Long planBoardId =
                RestAssured.given()
                        .header("Authorization", accessToken)
                        .body(planBoardSaveRequest)
                        .contentType(APPLICATION_JSON_VALUE)
                        .log()
                        .all()
                        .when()
                        .post("/plan")
                        .then()
                        .log()
                        .all()
                        .extract()
                        .body()
                        .as(Long.class);

        ExtractableResponse<Response> response =
                RestAssured.given()
                        .header("Authorization", accessToken)
                        .body(planBoardSaveRequest)
                        .contentType(APPLICATION_JSON_VALUE)
                        .log()
                        .all()
                        .when()
                        .get("/plan")
                        .then()
                        .log()
                        .all()
                        .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }
}
