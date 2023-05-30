package com.ssafy.enjoytrip.plan.service;

import com.ssafy.enjoytrip.plan.model.dto.*;
import com.ssafy.enjoytrip.plan.model.mapper.PlanMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlanServiceImpl implements PlanService{
    final private PlanMapper planMapper;

    @Transactional
    @Override
    public int savePlanBoard(PlanBoardRequest planBoardRequest){
        int result=1;
        try{
            PlanBoardDto planBoardDto = PlanBoardDto
                    .builder()
                    .startDate(Date.valueOf(planBoardRequest.getStartDate()))
                    .endDate(Date.valueOf(planBoardRequest.getEndDate()))
                    .title(planBoardRequest.getTitle())
                    .userId(planBoardRequest.getUserId())
                    .build();

            planMapper.insertPlanBoard(planBoardDto);
            for (List<Plan> planList:planBoardRequest.getPlanDateMap().values()) {
                for(Plan plan : planList){
                    plan.setPlanBoardId(planBoardDto.getPlanBoardId());
                }
                planMapper.insertPlanList(planList);
            }
        }catch (Exception e){
            e.printStackTrace();
            result = 0;
        }

        return result;
    }

    @Override
    public PlanBoardResponse search(int planBoardId) {
        PlanBoardResponse planBoardResponse = null;
        PlanBoardDto planBoardDto = planMapper.selectPlanBoardByPlanBoardId(planBoardId);
        List<Plan> planList = planMapper.selectPlanByPlanBoardId(planBoardId);
        HashMap<String,List<Plan>> planDateMap = new HashMap<>();

        for (int i=0; i<planList.size(); i++){
            if(planDateMap.containsKey(planList.get(i).getDate().toString())){
                planDateMap.get(planList.get(i).getDate().toString()).add(planList.get(i));
            }else{
                List<Plan> list = new ArrayList<Plan>();
                list.add(planList.get(i));
                planDateMap.put(planList.get(i).getDate().toString(),list);
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
        System.out.println(planBoardResponse);
        return planBoardResponse;
    }

    @Override
    public List<PlanBoardDto> list(String userId) {
        return planMapper.selectPlanBoardByUserId(userId);
    }
}
