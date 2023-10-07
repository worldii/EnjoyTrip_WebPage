package com.ssafy.enjoytrip.core.plan.model.dto;


import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class PlanBoard {

    private Long planBoardId;
    private String userId;
    private String title;
    private Date startDate;
    private Date endDate;

    @Builder
    public PlanBoard(
        final Long planBoardId, final String userId, final String title,
        final Date startDate, final Date endDate
    ) {
        this.planBoardId = planBoardId;
        this.userId = userId;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
