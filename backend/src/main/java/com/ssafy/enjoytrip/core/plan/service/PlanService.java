package com.ssafy.enjoytrip.core.plan.service;

import com.ssafy.enjoytrip.core.plan.model.dto.PlanBoardDto;
import com.ssafy.enjoytrip.core.plan.model.dto.PlanBoardRequest;
import com.ssafy.enjoytrip.core.plan.model.dto.PlanBoardResponse;
import java.util.List;

public interface PlanService {

    int savePlanBoard(PlanBoardRequest planBoardRequest);

    PlanBoardResponse search(int planBoardId);

    List<PlanBoardDto> list(String userId);
}
