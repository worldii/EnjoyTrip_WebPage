package com.ssafy.enjoytrip.core.plan.model.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PlanSaveRequest {

    private String placeName;
    private String content;
    private int planOrder;
    private Long expectDuration;
    private String expectDate;
    private String startTime;
    private String endTime;

}
