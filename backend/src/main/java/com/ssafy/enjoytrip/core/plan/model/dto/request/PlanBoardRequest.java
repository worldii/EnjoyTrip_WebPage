package com.ssafy.enjoytrip.core.plan.model.dto.request;


import com.ssafy.enjoytrip.core.plan.model.dto.Plan;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
public class PlanBoardRequest {

    private String userId;
    private String title;
    private Map<String, List<Plan>> planDateMap;
    private String startDate;
    private String endDate;
}
