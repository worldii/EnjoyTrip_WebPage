package com.ssafy.enjoytrip.plan.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.sql.Date;
import java.util.List;
import java.util.Map;

@Data
@ToString
@AllArgsConstructor
@Builder
public class PlanBoardResponse {
    private int planBoardId;
    private String userId;
    private String title;
    private String startDate;
    private String endDate;
    private Map<String, List<Plan>> planDateMap;
}
