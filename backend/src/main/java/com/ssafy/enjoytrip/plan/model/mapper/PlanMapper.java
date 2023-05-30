package com.ssafy.enjoytrip.plan.model.mapper;

import com.ssafy.enjoytrip.plan.model.dto.Plan;
import com.ssafy.enjoytrip.plan.model.dto.PlanBoardDto;
import org.apache.ibatis.annotations.Mapper;

import java.sql.Date;
import java.util.List;

@Mapper
public interface PlanMapper {
   int insertPlanBoard(PlanBoardDto planBoardDto);
   int insertPlanList(List<Plan> planDtoList);

   List<PlanBoardDto> selectPlanBoardByUserId(String userId);
   List<Plan> selectPlanByPlanBoardId(int planBoardId);
   PlanBoardDto selectPlanBoardByPlanBoardId(int planBoardId);

   int deletePlanList(int planBoardId, Date date);
}
