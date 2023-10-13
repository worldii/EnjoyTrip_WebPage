package com.ssafy.enjoytrip.core.plan.service;

import com.ssafy.enjoytrip.core.plan.model.dto.request.PlanBoardSaveRequest;
import com.ssafy.enjoytrip.core.plan.model.dto.response.PlanBoardResponse;
import java.util.List;

public interface PlanService {

    Long savePlanBoard(final PlanBoardSaveRequest planBoardRequest);

    List<PlanBoardResponse> selectAll(final String userId);

    PlanBoardResponse detail(final Long planBoardId, String userId);
}
