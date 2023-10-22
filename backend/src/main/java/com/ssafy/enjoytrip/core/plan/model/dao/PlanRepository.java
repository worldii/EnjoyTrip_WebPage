package com.ssafy.enjoytrip.core.plan.model.dao;

import com.ssafy.enjoytrip.core.plan.model.entity.Plan;
import com.ssafy.enjoytrip.core.plan.model.entity.PlanBoard;
import com.ssafy.enjoytrip.core.plan.model.mapper.PlanMapper;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Repository
public class PlanRepository {

    private final PlanMapper planMapper;

    @Transactional
    public int insertPlanBoard(final PlanBoard planBoard) {
        return planMapper.insertPlanBoard(planBoard);
    }

    @Transactional
    public int insertPlanList(final List<Plan> plans) {
        return planMapper.insertPlanList(plans);
    }

    @Transactional(readOnly = true)
    public List<Plan> selectPlanByPlanBoardId(final Long planBoardId) {
        return planMapper.selectPlanByPlanBoardId(planBoardId);
    }

    @Transactional(readOnly = true)
    public Optional<PlanBoard> selectPlanBoardByPlanBoardId(final Long planBoardId) {
        return planMapper.selectPlanBoardByPlanBoardId(planBoardId);
    }

    @Transactional(readOnly = true)
    public List<PlanBoard> selectPlanBoardByUserId(final String userId) {
        return planMapper.selectPlanBoardByUserId(userId);
    }
}
