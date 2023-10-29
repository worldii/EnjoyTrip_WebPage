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
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("Plan 테스트")
class PlanTest {

    @Test
    @DisplayName("Plan 생성 테스트")
    void createPlan() {
        // given & when & then
        assertThatCode(
                        () ->
                                Plan.builder()
                                        .expectDuration(1L)
                                        .planBoardId(1L)
                                        .placeName("placeName")
                                        .content("content")
                                        .planOrder(1)
                                        .expectDate(new Date(2021, 1, 1))
                                        .startTime(new Time(1, 1, 1))
                                        .endTime(new Time(1, 1, 1))
                                        .build())
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Plan 생성 테스트 - startTime이 endTime보다 빠를 경우")
    void createPlanWithStartTimeBeforeEndTime() {
        // given & when & then
        assertThatCode(
                        () ->
                                Plan.builder()
                                        .expectDuration(1L)
                                        .planBoardId(1L)
                                        .placeName("placeName")
                                        .content("content")
                                        .planOrder(1)
                                        .expectDate(new Date(2021, 1, 1))
                                        .startTime(new Time(1, 1, 1))
                                        .endTime(new Time(1, 1, 0))
                                        .build())
                .isInstanceOf(PlanException.class)
                .hasMessage("startTime은 endTime보다 빠를 수 없습니다.");
    }

    @NullSource
    @ParameterizedTest
    @DisplayName("Plan 생성 테스트 - planBoardId가 null일 경우")
    void createPlanWithNullPlanBoardId(final Long planBoardId) {
        // given & when & then
        assertThatCode(
                        () ->
                                Plan.builder()
                                        .expectDuration(1L)
                                        .planBoardId(planBoardId)
                                        .placeName("placeName")
                                        .content("content")
                                        .planOrder(1)
                                        .expectDate(new Date(2021, 1, 1))
                                        .startTime(new Time(1, 1, 1))
                                        .endTime(new Time(1, 1, 1))
                                        .build())
                .isInstanceOf(PlanException.class)
                .hasMessage("plan의 planBoardId는 null이 될 수 없습니다.");
    }

    @NullSource
    @ParameterizedTest
    @DisplayName("Plan 생성 테스트 - placeName이 null일 경우")
    void createPlanWithNullPlaceName(final String placeName) {
        // given & when & then
        assertThatCode(
                        () ->
                                Plan.builder()
                                        .expectDuration(1L)
                                        .planBoardId(1L)
                                        .placeName(placeName)
                                        .content("content")
                                        .planOrder(1)
                                        .expectDate(new Date(2021, 1, 1))
                                        .startTime(new Time(1, 1, 1))
                                        .endTime(new Time(1, 1, 1))
                                        .build())
                .isInstanceOf(PlanException.class)
                .hasMessage("plan의 placeName은 null이 될 수 없습니다.");
    }

    @NullSource
    @ParameterizedTest
    @DisplayName("Plan 생성 테스트 - content가 null일 경우")
    void createPlanWithNullContent(final String content) {
        // given & when & then
        assertThatCode(
                        () ->
                                Plan.builder()
                                        .expectDuration(1L)
                                        .planBoardId(1L)
                                        .placeName("placeName")
                                        .content(content)
                                        .planOrder(1)
                                        .expectDate(new Date(2021, 1, 1))
                                        .startTime(new Time(1, 1, 1))
                                        .endTime(new Time(1, 1, 1))
                                        .build())
                .isInstanceOf(PlanException.class)
                .hasMessage("plan의 content는 null이 될 수 없습니다.");
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0})
    @DisplayName("Plan 생성 테스트 - planOrder가 0이하일 경우")
    void createPlanWithPlanOrderLessThanZero(final int planOrder) {
        // given & when & then
        assertThatCode(
                        () ->
                                Plan.builder()
                                        .expectDuration(1L)
                                        .planBoardId(1L)
                                        .placeName("placeName")
                                        .content("content")
                                        .planOrder(planOrder)
                                        .expectDate(new Date(2021, 1, 1))
                                        .startTime(new Time(1, 1, 1))
                                        .endTime(new Time(1, 1, 1))
                                        .build())
                .isInstanceOf(PlanException.class)
                .hasMessage("plan의 planOrder는 0보다 작을 수 없습니다.");
    }

    @NullSource
    @DisplayName("Plan 생성 테스트 - expectDate가 null일 경우")
    void createPlanWithNullExpectDate(final Date expectDate) {
        // given & when & then
        assertThatCode(
                        () ->
                                Plan.builder()
                                        .expectDuration(1L)
                                        .planBoardId(1L)
                                        .placeName("placeName")
                                        .content("content")
                                        .planOrder(1)
                                        .expectDate(expectDate)
                                        .startTime(new Time(1, 1, 1))
                                        .endTime(new Time(1, 1, 1))
                                        .build())
                .isInstanceOf(PlanException.class)
                .hasMessage("plan의 expectDate는 null이 될 수 없습니다.");
    }

    @NullSource
    @DisplayName("Plan 생성 테스트 - startTime이 null일 경우")
    void createPlanWithNullStartTime(final Time startTime) {
        // given & when & then
        assertThatCode(
                        () ->
                                Plan.builder()
                                        .expectDuration(1L)
                                        .planBoardId(1L)
                                        .placeName("placeName")
                                        .content("content")
                                        .planOrder(1)
                                        .expectDate(new Date(2021, 1, 1))
                                        .startTime(startTime)
                                        .endTime(new Time(1, 1, 1))
                                        .build())
                .isInstanceOf(PlanException.class)
                .hasMessage("plan의 startTime은 null이 될 수 없습니다.");
    }

    @NullSource
    @DisplayName("Plan 생성 테스트 - endTime이 null일 경우")
    void createPlanWithNullEndTime(final Time endTime) {
        // given & when & then
        assertThatCode(
                        () ->
                                Plan.builder()
                                        .expectDuration(1L)
                                        .planBoardId(1L)
                                        .placeName("placeName")
                                        .content("content")
                                        .planOrder(1)
                                        .expectDate(new Date(2021, 1, 1))
                                        .startTime(new Time(1, 1, 1))
                                        .endTime(endTime)
                                        .build())
                .isInstanceOf(PlanException.class)
                .hasMessage("plan의 endTime은 null이 될 수 없습니다.");
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -2})
    @DisplayName("Plan 생성 테스트 - expectDuration이 0이하일 경우")
    void createPlanWithExpectDurationLessThanZero(final long expectDuration) {
        // given & when & then
        assertThatCode(
                        () ->
                                Plan.builder()
                                        .expectDuration(expectDuration)
                                        .planBoardId(1L)
                                        .placeName("placeName")
                                        .content("content")
                                        .planOrder(1)
                                        .expectDate(new Date(2021, 1, 1))
                                        .startTime(new Time(1, 1, 1))
                                        .endTime(new Time(1, 1, 1))
                                        .build())
                .isInstanceOf(PlanException.class)
                .hasMessage("plan의 expectDuration은 0보다 작을 수 없습니다.");
    }
}
