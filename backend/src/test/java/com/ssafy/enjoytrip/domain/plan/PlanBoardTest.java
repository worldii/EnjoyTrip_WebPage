package com.ssafy.enjoytrip.domain.plan;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

import com.ssafy.enjoytrip.core.plan.model.entity.PlanBoard;
import com.ssafy.enjoytrip.global.error.PlanException;
import java.sql.Date;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("PlanBoard 테스트")
public class PlanBoardTest {

    @Test
    @DisplayName("PlanBoard 생성 테스트")
    void createPlanBoard() {
        // given // when // then
        assertThatCode(() -> {
            PlanBoard.builder()
                .userId("test")
                .title("test")
                .startDate(new Date(2021, 1, 1))
                .endDate(new Date(2021, 1, 2))
                .build();
        }).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("PlanBoard 생성 테스트 - userId가 null일 때")
    void createPlanBoardWithNullUserId() {
        // given & when & then
        assertThatCode(() -> {
            PlanBoard.builder()
                .userId(null)
                .title("test")
                .startDate(new Date(2021, 1, 1))
                .endDate(new Date(2021, 1, 2))
                .build();
        }).isInstanceOf(PlanException.class)
            .hasMessage("userId는 null일 수 없습니다.");
    }

    @Test
    @DisplayName("PlanBoard 생성 테스트 - title이 null일 때")
    void createPlanBoardWithNullTitle() {
        // given & when & then
        assertThatCode(() -> {
            PlanBoard.builder()
                .userId("test")
                .title(null)
                .startDate(new Date(2021, 1, 1))
                .endDate(new Date(2021, 1, 2))
                .build();
        }).isInstanceOf(PlanException.class)
            .hasMessage("title은 null일 수 없습니다.");
    }

    @Test
    @DisplayName("PlanBoard 생성 테스트 - startDate가 null일 때")
    void createPlanBoardWithNullStartDate() {
        // given & when & then
        assertThatCode(() -> {
            PlanBoard.builder()
                .userId("test")
                .title("test")
                .startDate(null)
                .endDate(new Date(2021, 1, 2))
                .build();
        }).isInstanceOf(PlanException.class)
            .hasMessage("startDate는 null일 수 없습니다.");
    }

    @Test
    @DisplayName("PlanBoard 생성 테스트 - endDate가 null일 때")
    void createPlanBoardWithNullEndDate() {
        // given & when & then
        assertThatCode(() -> {
            PlanBoard.builder()
                .userId("test")
                .title("test")
                .startDate(new Date(2021, 1, 1))
                .endDate(null)
                .build();
        }).isInstanceOf(PlanException.class)
            .hasMessage("endDate는 null일 수 없습니다.");
    }

    @Test
    @DisplayName("PlanBoard 생성 테스트 - startDate가 endDate보다 빠를 때")
    void createPlanBoardWithStartDateBeforeEndDate() {
        // given & when & then
        assertThatCode(() -> {
            PlanBoard.builder()
                .userId("test")
                .title("test")
                .startDate(new Date(2021, 1, 2))
                .endDate(new Date(2021, 1, 1))
                .build();
        }).isInstanceOf(PlanException.class)
            .hasMessage("startDate는 endDate보다 빠를 수 없습니다.");
    }

    @Test
    @DisplayName("PlanBoard 생성 테스트 - startDate가 endDate와 같을 때")
    void createPlanBoardWithStartDateSameAsEndDate() {
        // given & when & then
        assertThatCode(() -> {
            PlanBoard.builder()
                .userId("test")
                .title("test")
                .startDate(new Date(2021, 1, 1))
                .endDate(new Date(2021, 1, 1))
                .build();
        }).doesNotThrowAnyException();
    }
}
