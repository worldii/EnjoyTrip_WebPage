package com.ssafy.enjoytrip.plan.service;

import com.ssafy.enjoytrip.plan.model.dto.PlanBoardDto;
import com.ssafy.enjoytrip.plan.model.dto.PlanBoardRequest;
import com.ssafy.enjoytrip.plan.model.dto.PlanBoardResponse;

import java.util.List;

public interface PlanService {
    int savePlanBoard(PlanBoardRequest planBoardRequest);
    PlanBoardResponse search(int planBoardId);
    List<PlanBoardDto> list(String userId);
}
