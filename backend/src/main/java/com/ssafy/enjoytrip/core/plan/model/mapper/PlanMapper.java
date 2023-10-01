package com.ssafy.enjoytrip.core.plan.model.mapper;

import com.ssafy.enjoytrip.core.plan.model.dto.Plan;
import com.ssafy.enjoytrip.core.plan.model.dto.PlanBoardDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PlanMapper {

    int insertPlanBoard(PlanBoardDto planBoardDto);

    int insertPlanList(List<Plan> planDtoList);

    List<PlanBoardDto> selectPlanBoardByUserId(String userId);

    List<Plan> selectPlanByPlanBoardId(int planBoardId);

    PlanBoardDto selectPlanBoardByPlanBoardId(int planBoardId);
}
