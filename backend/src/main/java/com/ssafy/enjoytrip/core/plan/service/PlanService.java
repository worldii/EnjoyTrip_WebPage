package com.ssafy.enjoytrip.core.plan.service;

import com.ssafy.enjoytrip.core.plan.model.dto.PlanBoardDto;
import com.ssafy.enjoytrip.core.plan.model.dto.request.PlanBoardRequest;
import com.ssafy.enjoytrip.core.plan.model.dto.response.PlanBoardResponse;
import java.util.List;

public interface PlanService {

    int savePlanBoard(PlanBoardRequest planBoardRequest);

    List<PlanBoardDto> list(String userId);

    PlanBoardResponse detail(Long planBoardId, String userId);
}
