package com.ssafy.enjoytrip.core.plan.model.dto.response;

import com.ssafy.enjoytrip.core.plan.model.entity.Plan;
import java.sql.Date;
import java.sql.Time;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
@AllArgsConstructor
public class PlanResponse {

    private Long planId;
    private Long planBoardId;
    private String placeName;
    private String content;
    private int planOrder;
    private Long expectDuration;
    private Date expectDate;
    private Time startTime;
    private Time endTime;

    public static PlanResponse from(final Plan plan) {
        return new PlanResponse(
            plan.getPlanId(),
            plan.getPlanBoardId(),
            plan.getPlaceName(),
            plan.getContent(),
            plan.getPlanOrder(),
            plan.getExpectDuration(),
            plan.getExpectDate(),
            plan.getStartTime(),
            plan.getEndTime()
        );
    }
}
