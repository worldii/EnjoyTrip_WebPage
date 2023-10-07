package com.ssafy.enjoytrip.core.plan.model.dao;

import com.ssafy.enjoytrip.core.plan.model.dto.Plan;
import com.ssafy.enjoytrip.core.plan.model.dto.PlanBoardDto;
import com.ssafy.enjoytrip.core.plan.model.mapper.PlanMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Repository
public class PlanRepository {

    private final PlanMapper planMapper;

    @Transactional
    public int insertPlanBoard(PlanBoardDto planBoardDto) {
        return planMapper.insertPlanBoard(planBoardDto);
    }

    @Transactional
    public int insertPlanList(List<Plan> planDtoList) {
        return planMapper.insertPlanList(planDtoList);
    }

    @Transactional(readOnly = true)
    public List<PlanBoardDto> selectPlanBoardByUserId(String userId) {
        return planMapper.selectPlanBoardByUserId(userId);
    }

    @Transactional(readOnly = true)
    public List<Plan> selectPlanByPlanBoardId(Long planBoardId) {
        return planMapper.selectPlanByPlanBoardId(planBoardId);
    }

    @Transactional(readOnly = true)
    public PlanBoardDto selectPlanBoardByPlanBoardId(Long planBoardId) {
        return planMapper.selectPlanBoardByPlanBoardId(planBoardId);
    }
}
