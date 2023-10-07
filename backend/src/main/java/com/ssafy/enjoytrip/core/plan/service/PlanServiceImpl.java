package com.ssafy.enjoytrip.core.plan.service;

import com.ssafy.enjoytrip.core.plan.model.dao.PlanRepository;
import com.ssafy.enjoytrip.core.plan.model.dto.Plan;
import com.ssafy.enjoytrip.core.plan.model.dto.PlanBoardDto;
import com.ssafy.enjoytrip.core.plan.model.dto.request.PlanBoardRequest;
import com.ssafy.enjoytrip.core.plan.model.dto.response.PlanBoardResponse;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    public int savePlanBoard(PlanBoardRequest planBoardRequest) {
        PlanBoardDto planBoardDto = PlanBoardDto
            .builder()
            .startDate(Date.valueOf(planBoardRequest.getStartDate()))
            .endDate(Date.valueOf(planBoardRequest.getEndDate()))
            .title(planBoardRequest.getTitle())
            .userId(planBoardRequest.getUserId())
            .build();
        planRepository.insertPlanBoard(planBoardDto);

        for (List<Plan> planList : planBoardRequest.getPlanDateMap().values()) {
            for (Plan plan : planList) {
                plan.setPlanBoardId(planBoardDto.getPlanBoardId());
            }
            planRepository.insertPlanList(planList);
        }
        return 0;
    }

    @Override
    public PlanBoardResponse detail(Long planBoardId, String userId) {
        PlanBoardResponse planBoardResponse = null;
        PlanBoardDto planBoardDto = planRepository.selectPlanBoardByPlanBoardId(planBoardId);
        List<Plan> planList = planRepository.selectPlanByPlanBoardId(planBoardId);
        HashMap<String, List<Plan>> planDateMap = new HashMap<>();

        for (int i = 0; i < planList.size(); i++) {
            if (planDateMap.containsKey(planList.get(i).getDate().toString())) {
                planDateMap.get(planList.get(i).getDate().toString()).add(planList.get(i));
            } else {
                List<Plan> list = new ArrayList<>();
                list.add(planList.get(i));
                planDateMap.put(planList.get(i).getDate().toString(), list);
            }
        }

        planBoardResponse = PlanBoardResponse
            .builder()
            .planBoardId(planBoardDto.getPlanBoardId())
            .title(planBoardDto.getTitle())
            .userId(planBoardDto.getUserId())
            .startDate(planBoardDto.getStartDate().toString())
            .endDate(planBoardDto.getEndDate().toString())
            .planDateMap(planDateMap)
            .build();
        return planBoardResponse;
    }

    @Override
    public List<PlanBoardDto> list(String userId) {
        return planRepository.selectPlanBoardByUserId(userId);
    }
}
