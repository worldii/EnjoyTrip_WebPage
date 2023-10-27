package com.ssafy.enjoytrip.core.plan.model.dto.request;


import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
public class PlanBoardSaveRequest {

    private String userId;
    private String title;
    private String startDate;
    private String endDate;
    private List<PlanSaveRequest> planList;
}
