package com.ssafy.enjoytrip.core.plan.service;

import com.ssafy.enjoytrip.core.plan.model.dto.request.PlanBoardRequest;
import com.ssafy.enjoytrip.core.plan.model.dto.response.PlanBoardResponse;
import com.ssafy.enjoytrip.core.plan.model.entity.PlanBoard;
import java.util.List;

public interface PlanService {

    Long savePlanBoard(PlanBoardRequest planBoardRequest);

    List<PlanBoard> list(String userId);

    PlanBoardResponse detail(Long planBoardId, String userId);
}
