package com.ssafy.enjoytrip.plan.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.sql.Date;

@Data
@ToString
@AllArgsConstructor
@Builder
public class PlanBoardDto {
    private int planBoardId;
    private String userId;
    private String title;
    private Date startDate;
    private Date endDate;
}
