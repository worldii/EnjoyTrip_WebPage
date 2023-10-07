package com.ssafy.enjoytrip.core.plan.model.dto.response;

import com.ssafy.enjoytrip.core.plan.model.dto.Plan;
import java.util.List;
import java.util.Map;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class PlanBoardResponse {

    private Long planBoardId;
    private String userId;
    private String title;
    private String startDate;
    private String endDate;
    private Map<String, List<Plan>> planDateMap;
}
