package com.ssafy.enjoytrip.core.plan.model.mapper;

import com.ssafy.enjoytrip.core.plan.model.entity.Plan;
import com.ssafy.enjoytrip.core.plan.model.entity.PlanBoard;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PlanMapper {

    int insertPlanBoard(final PlanBoard planBoardDto);

    int insertPlanList(final List<Plan> planDtoList);

    List<PlanBoard> selectPlanBoardByUserId(final String userId);

    List<Plan> selectPlanByPlanBoardId(final Long planBoardId);

    Optional<PlanBoard> selectPlanBoardByPlanBoardId(final Long planBoardId);
}
