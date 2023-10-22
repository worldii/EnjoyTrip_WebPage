package com.ssafy.enjoytrip.core.plan.model.entity;

import com.ssafy.enjoytrip.global.error.PlanException;
import java.sql.Date;
import java.sql.Time;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Plan {

    private Long planId;
    private Long planBoardId;
    private String placeName;
    private String content;
    private int planOrder;
    private Long expectDuration;
    private Date expectDate;
    private Time startTime;
    private Time endTime;

    @Builder
    public Plan(
        final Long planId, final Long planBoardId,
        final String placeName, final String content,
        final int planOrder, final Long expectDuration,
        final Date expectDate,
        final Time startTime, final Time endTime
    ) {
        validatePlaceName(placeName);
        validateContent(content);
        validatePlanOrder(planOrder);
        validateExpectDuration(expectDuration);
        validateExpectDate(expectDate);
        validateTime(startTime, endTime);
        this.planId = planId;
        this.planBoardId = planBoardId;
        this.placeName = placeName;
        this.content = content;
        this.planOrder = planOrder;
        this.expectDuration = expectDuration;
        this.expectDate = expectDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    private void validateTime(Time startTime, Time endTime) {
        validateStartTime(startTime);
        validateEndTime(endTime);
        if (startTime.after(endTime)) {
            throw new PlanException("startTime은 endTime보다 빠를 수 없습니다.");
        }
    }

    private void validatePlaceName(String placeName) {
        if (placeName == null) {
            throw new PlanException("plan의 placeName은 null이 될 수 없습니다.");
        }
    }

    private void validateContent(String content) {
        if (content == null) {
            throw new PlanException("plan의 content는 null이 될 수 없습니다.");
        }
    }

    private void validatePlanOrder(int planOrder) {
        if (planOrder <= 0) {
            throw new PlanException("plan의 planOrder는 0보다 작을 수 없습니다.");
        }
    }

    private void validateExpectDuration(Long expectDuration) {
        if (expectDuration < 0) {
            throw new PlanException("plan의 expectDuration은 0보다 작을 수 없습니다.");
        }
    }

    private void validateExpectDate(Date expectDate) {
        if (expectDate == null) {
            throw new PlanException("plan의 expectDate는 null이 될 수 없습니다.");
        }
    }

    private void validateStartTime(Time startTime) {
        if (startTime == null) {
            throw new PlanException("plan의 startTime은 null이 될 수 없습니다.");
        }
    }

    private void validateEndTime(Time endTime) {
        if (endTime == null) {
            throw new PlanException("plan의 endTime은 null이 될 수 없습니다.");
        }
    }
}
