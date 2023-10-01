package com.ssafy.enjoytrip.core.plan.model.dto;

import java.sql.Date;
import java.sql.Time;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@Builder
public class Plan {

    private int planId;
    private int planBoardId;
    private String place;
    private String content;
    private int order;
    private int duration;
    private Date date;
    private Time startTime;
    private Time endTime;
}
