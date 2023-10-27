package com.ssafy.enjoytrip.integration.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.ssafy.enjoytrip.core.plan.model.dto.request.PlanBoardSaveRequest;
import com.ssafy.enjoytrip.core.plan.model.dto.request.PlanSaveRequest;
import com.ssafy.enjoytrip.core.plan.model.dto.response.PlanBoardResponse;
import com.ssafy.enjoytrip.core.plan.service.PlanService;
import java.sql.Date;
import java.sql.Time;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@DisplayName("PlanService 테스트")
class PlanServiceTest {

    @Autowired
    private PlanService planService;

    @Test
    @DisplayName("Plan Board 등록")
    @Sql("/truncate.sql")
    void createPlanBoard() {
        // given
        List<PlanSaveRequest> plans = List.of(
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
                .build()
        );

        PlanBoardSaveRequest planBoardSaveRequest = PlanBoardSaveRequest.builder()
            .title("test")
            .startDate("2021-01-01")
            .endDate("2021-01-02")
            .planList(plans)
            .userId("test")
            .title("test")
            .build();

        // when
        Long boardId = planService.savePlanBoard(planBoardSaveRequest);

        // then
        assertThat(boardId).isNotNull();
    }

    @Test
    @DisplayName("Plan Board 조회")
    @Sql({"/truncate.sql", "/user.sql"})
    void planBoardDetail() {
        // given
        List<PlanSaveRequest> plans = List.of(
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
                .build()
        );

        PlanBoardSaveRequest planBoardSaveRequest = PlanBoardSaveRequest.builder()
            .title("test")
            .startDate("2021-01-01")
            .endDate("2021-01-02")
            .planList(plans)
            .userId("test")
            .title("test")
            .build();

        Long boardId = planService.savePlanBoard(planBoardSaveRequest);

        // when
        PlanBoardResponse planBoard = planService.detail(boardId, "test");

        // then
        assertAll(
            () -> assertThat(planBoard.getTitle()).isEqualTo("test"),
            () -> assertThat(planBoard.getStartDate()).isEqualTo("2021-01-01"),
            () -> assertThat(planBoard.getEndDate()).isEqualTo("2021-01-02"),
            () -> assertThat(planBoard.getPlanList().size()).isEqualTo(2)
        );
    }

    @Test
    @DisplayName("Plan Board 전체 조회")
    @Sql({"/truncate.sql", "/user.sql"})
    void planBoardSelectAll() {
        // given
        List<PlanSaveRequest> plans = List.of(
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
                .build()
        );

        PlanBoardSaveRequest planBoardSaveRequest = PlanBoardSaveRequest.builder()
            .title("test")
            .startDate("2021-01-01")
            .endDate("2021-01-02")
            .planList(plans)
            .userId("test")
            .title("test")
            .build();

        planService.savePlanBoard(planBoardSaveRequest);

        // when
        List<PlanBoardResponse> planBoardList = planService.selectAll("test");

        // then
        assertThat(planBoardList.size()).isEqualTo(1);
    }


}
