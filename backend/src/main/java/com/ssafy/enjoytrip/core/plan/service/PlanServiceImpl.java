package com.ssafy.enjoytrip.core.plan.service;

import com.ssafy.enjoytrip.core.plan.model.dao.PlanRepository;
import com.ssafy.enjoytrip.core.plan.model.dto.request.PlanBoardSaveRequest;
import com.ssafy.enjoytrip.core.plan.model.dto.response.PlanBoardResponse;
import com.ssafy.enjoytrip.core.plan.model.dto.response.PlanResponse;
import com.ssafy.enjoytrip.core.plan.model.entity.Plan;
import com.ssafy.enjoytrip.core.plan.model.entity.PlanBoard;
import com.ssafy.enjoytrip.global.error.PlanException;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlanServiceImpl implements PlanService {

    private final PlanRepository planRepository;

    @Transactional
    @Override
    public Long savePlanBoard(final PlanBoardSaveRequest planBoardRequest) {
        final PlanBoard planBoard = PlanBoard
            .builder()
            .startDate(Date.valueOf(planBoardRequest.getStartDate()))
            .endDate(Date.valueOf(planBoardRequest.getEndDate()))
            .title(planBoardRequest.getTitle())
            .userId(planBoardRequest.getUserId())
            .build();

        planRepository.insertPlanBoard(planBoard);

        final List<Plan> plans = planBoardRequest.getPlanList().stream()
            .map(plan -> Plan.builder()
                .planBoardId(planBoard.getPlanBoardId())
                .planOrder(plan.getPlanOrder())
                .expectDuration(plan.getExpectDuration())
                .startTime(plan.getStartTime())
                .endTime(plan.getEndTime())
                .placeName(plan.getPlaceName())
                .content(plan.getContent())
                .build()).collect(Collectors.toList());

        planRepository.insertPlanList(plans);
        return planBoard.getPlanBoardId();
    }

    @Override
    public PlanBoardResponse detail(final Long planBoardId, final String userId) {
        final PlanBoard planBoard = planRepository.selectPlanBoardByPlanBoardId(planBoardId)
            .orElseThrow(() -> new PlanException("해당 일정이 존재하지 않습니다."));
        
        final List<PlanResponse> plans = planRepository.selectPlanByPlanBoardId(planBoardId)
            .stream()
            .map(PlanResponse::from)
            .collect(Collectors.toList());

        return PlanBoardResponse.of(planBoard, plans);
    }

    @Override
    public List<PlanBoardResponse> selectAll(final String userId) {
        return planRepository.selectPlanBoardByUserId(userId)
            .stream()
            .map(PlanBoardResponse::from)
            .collect(Collectors.toList());
    }
}
