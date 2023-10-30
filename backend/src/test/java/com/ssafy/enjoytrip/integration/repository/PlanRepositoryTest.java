package com.ssafy.enjoytrip.integration.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

import com.ssafy.enjoytrip.core.plan.model.dao.PlanRepository;
import com.ssafy.enjoytrip.core.plan.model.entity.Plan;
import com.ssafy.enjoytrip.core.plan.model.entity.PlanBoard;
import java.sql.Date;
import java.sql.Time;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@DisplayName("PlanRepository 테스트")
@SpringBootTest
class PlanRepositoryTest {

    @Autowired private PlanRepository planRepository;

    @Test
    @DisplayName("Plan 생성 테스트")
    @Sql("/truncate.sql")
    void createPlan() {
        // given
        Plan plan =
                Plan.builder()
                        .planBoardId(1L)
                        .placeName("test")
                        .planOrder(1)
                        .content("test")
                        .startTime(new Time(1, 1, 1))
                        .endTime(new Time(1, 1, 2))
                        .expectDuration(1L)
                        .expectDate(new Date(2021, 1, 1))
                        .build();

        // when // then
        assertThatCode(() -> planRepository.insertPlanList(List.of(plan)))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Plan Board 등록")
    @Sql("/truncate.sql")
    void createPlanBoard() {
        // given
        String userId = "test";
        String title = "test";
        PlanBoard planBoard =
                PlanBoard.builder()
                        .userId(userId)
                        .startDate(new Date(2021, 1, 1))
                        .endDate(new Date(2021, 1, 2))
                        .title(title)
                        .build();

        // when // then
        assertThatCode(() -> planRepository.insertPlanBoard(planBoard)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Plan Board 조회")
    @Sql({"/truncate.sql", "/planBoard.sql"})
    void selectPlanBoard() {
        // given // when // then
        assertThat(planRepository.selectPlanBoardByPlanBoardId(1L)).isNotNull();
    }

    @Test
    @DisplayName("Plan Board 를 userId로 조회")
    @Sql({"/truncate.sql", "/planBoard.sql"})
    void selectPlanBoardByUserId() {
        // given
        String userId = "user1";

        // when // then
        assertThat(planRepository.selectPlanBoardByUserId("test")).isNotNull();
    }

    @Test
    @DisplayName("Plan을 planBoardId로 조회")
    @Sql({"/truncate.sql", "/plan.sql"})
    void selectPlanByPlanBoardId() {
        // given
        Long planBoardId = 1L;

        // when // then
        assertThat(planRepository.selectPlanByPlanBoardId(planBoardId)).isNotNull();
    }
}
