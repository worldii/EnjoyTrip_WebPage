package com.ssafy.enjoytrip.core.plan.model.dto;

import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@Builder
public class PlanBoardDto {

    private Long planBoardId;
    private String userId;
    private String title;
    private Date startDate;
    private Date endDate;
}
