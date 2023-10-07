package com.ssafy.enjoytrip.core.plan.model.dto;

import java.sql.Date;
import java.sql.Time;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class Plan {

    private Long planId;
    private Long planBoardId;
    private String place;
    private String content;
    private int order;
    private int duration;
    private Date date;
    private Time startTime;
    private Time endTime;

}
