package com.ssafy.enjoytrip.plan.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.sql.Date;
import java.sql.Time;

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
