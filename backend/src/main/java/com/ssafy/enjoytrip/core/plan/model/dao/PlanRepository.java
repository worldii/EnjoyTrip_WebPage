package com.ssafy.enjoytrip.core.plan.model.dao;

import com.ssafy.enjoytrip.core.plan.model.entity.Plan;
import com.ssafy.enjoytrip.core.plan.model.entity.PlanBoard;
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
    public int insertPlanBoard(PlanBoard planBoardDto) {
        return planMapper.insertPlanBoard(planBoardDto);
    }

    @Transactional
    public int insertPlanList(List<Plan> planDtoList) {
        return planMapper.insertPlanList(planDtoList);
    }

    @Transactional(readOnly = true)
    public List<PlanBoard> selectPlanBoardByUserId(String userId) {
        return planMapper.selectPlanBoardByUserId(userId);
    }

    @Transactional(readOnly = true)
    public List<Plan> selectPlanByPlanBoardId(Long planBoardId) {
        return planMapper.selectPlanByPlanBoardId(planBoardId);
    }

    @Transactional(readOnly = true)
    public PlanBoard selectPlanBoardByPlanBoardId(Long planBoardId) {
        return planMapper.selectPlanBoardByPlanBoardId(planBoardId);
    }
}
