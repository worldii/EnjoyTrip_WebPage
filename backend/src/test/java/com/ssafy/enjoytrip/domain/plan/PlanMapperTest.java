package com.ssafy.enjoytrip.domain.plan;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.ssafy.enjoytrip.core.plan.model.entity.Plan;
import com.ssafy.enjoytrip.core.plan.model.entity.PlanBoard;
import com.ssafy.enjoytrip.core.plan.model.mapper.PlanMapper;
import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Slf4j
@Disabled
class PlanMapperTest {

    @Autowired
    PlanMapper planMapper;
    static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Test
    void testSelectPlanBoardByPlanBoardId() {
        // given
        Long planBoardId = 1L;

        // when
        PlanBoard planBoard = planMapper.selectPlanBoardByPlanBoardId(planBoardId);

        // then
        System.out.println(planBoard);
        assertNotNull(planBoard);
        assertEquals(planBoardId, planBoard.getPlanBoardId());
    }

    @Test
    void testSelectPlanBoardByUserId() {
        // given
        String userId = "test";

        // when
        List<PlanBoard> planBoardDtoList = planMapper.selectPlanBoardByUserId(userId);

        // then
        System.out.println(planBoardDtoList);
        assertEquals(1, planBoardDtoList.size());
        assertEquals(planBoardDtoList.get(0).getUserId(), userId);
    }

    @Test
    @Transactional
    void testInsertPlanBoardDto() {
        // given
        PlanBoard insertPlanBoardDto = makePlanBoardDto();
        System.out.println("insertPlanBoardDto = " + insertPlanBoardDto);
        // when
        int result = planMapper.insertPlanBoard(insertPlanBoardDto);

        // then
        PlanBoard selectPlanBoardDto = planMapper.selectPlanBoardByPlanBoardId(
            insertPlanBoardDto.getPlanBoardId());

        assertEquals(1, result);
        assertEquals(insertPlanBoardDto.getPlanBoardId(), selectPlanBoardDto.getPlanBoardId());
    }

    @Test
    void selectPlanByPlanBoardId() {
        // given
        Long planBoardId = 1L;

        // when
        List<Plan> planList = planMapper.selectPlanByPlanBoardId(planBoardId);

        // then
        assertEquals(4, planList.size());
        for (int i = 0; i < planList.size(); i++) {
            assertEquals(planList.get(i).getPlanBoardId(), planBoardId);
        }
    }

    @Test
    @Transactional
    void testInsertPlanDto() {
        // given
        Long planBoardId = 2L;
        int insertSize = 3;
        int beforeSize = planMapper.selectPlanByPlanBoardId(planBoardId).size();
        List<Plan> insertPlanList = makePlanDtoList(insertSize, planBoardId);
        System.out.println("insertPlanList = " + insertPlanList);

        // when
        int result = planMapper.insertPlanList(insertPlanList);

        // then
        List<Plan> selectPlanList = planMapper.selectPlanByPlanBoardId(planBoardId);
        System.out.println("selectPlanList = " + selectPlanList);
        System.out.println(result);
        assertEquals(result, insertSize);
        assertEquals(insertSize + beforeSize, selectPlanList.size());
    }


    List<Plan> makePlanDtoList(int size, Long planBoardId) {
        List<Plan> planDtoList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            planDtoList.add(makePlanDto(i, planBoardId));
        }
        return planDtoList;
    }

    Plan makePlanDto(int order, Long planBoardId) {
        return Plan.builder()
            .planBoardId(planBoardId)
            .placeName("test")
            .planOrder(order)
            .expectDate(Date.valueOf("2023-05-05"))
            .startTime(Time.valueOf("09:00:" + order))
            .endTime(Time.valueOf("09:00:" + (order + 1)))
            .build();
    }

    PlanBoard makePlanBoardDto() {
        return PlanBoard
            .builder()
            .userId("test")
            .title("test")
            .startDate(Date.valueOf("2023-05-05"))
            .endDate(Date.valueOf("2023-05-07"))
            .build();
    }
}
