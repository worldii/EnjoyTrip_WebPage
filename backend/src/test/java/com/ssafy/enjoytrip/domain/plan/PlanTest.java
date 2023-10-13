package com.ssafy.enjoytrip.domain.plan;


import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

import com.ssafy.enjoytrip.core.plan.model.entity.Plan;
import com.ssafy.enjoytrip.global.error.PlanException;
import java.sql.Date;
import java.sql.Time;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

@DisplayName("Plan 테스트")
public class PlanTest {

    @Test
    @DisplayName("Plan 생성 테스트")
    void createPlan() {
        // given & when & then
        assertThatCode(() -> Plan.builder()
            .expectDuration(1L)
            .planBoardId(1L)
            .placeName("placeName")
            .content("content")
            .planOrder(1)
            .expectDate(new Date(2021, 1, 1))
            .startTime(new Time(1, 1, 1))
            .endTime(new Time(1, 1, 1))
        ).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Plan 생성 테스트 - startTime이 endTime보다 빠를 경우")
    void createPlanWithStartTimeBeforeEndTime() {
        // given & when & then
        assertThatCode(() -> Plan.builder()
            .expectDuration(1L)
            .planBoardId(1L)
            .placeName("placeName")
            .content("content")
            .planOrder(1)
            .expectDate(new Date(2021, 1, 1))
            .startTime(new Time(1, 1, 1))
            .endTime(new Time(1, 1, 0))
        )
            .isInstanceOf(PlanException.class)
            .hasMessage("startTime은 endTime보다 빠를 수 없습니다.");
    }

    @NullSource
    @ParameterizedTest
    @DisplayName("Plan 생성 테스트 - planBoardId가 null일 경우")
    void createPlanWithNullPlanBoardId(final Long planBoardId) {
        // given & when & then
        assertThatCode(() -> Plan.builder()
            .expectDuration(1L)
            .planBoardId(planBoardId)
            .placeName("placeName")
            .content("content")
            .planOrder(1)
            .expectDate(new Date(2021, 1, 1))
            .startTime(new Time(1, 1, 1))
            .endTime(new Time(1, 1, 1))
        )
            .isInstanceOf(PlanException.class)
            .hasMessage("planBoardId는 null일 수 없습니다.");
    }

}
