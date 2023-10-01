package com.ssafy.enjoytrip.core.plan.model.dto;


import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@Builder
public class PlanBoardRequest {

    private int planBoardId;
    private String userId;
    private String title;
    private String startDate;
    private String endDate;
    private Map<String, List<Plan>> planDateMap;
}
