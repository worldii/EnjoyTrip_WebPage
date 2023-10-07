package com.ssafy.enjoytrip.domain.plan;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.ssafy.enjoytrip.core.plan.model.dto.Plan;
import com.ssafy.enjoytrip.core.plan.model.dto.PlanBoardDto;
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
    void testLoadBean() {
        // given - when -then
        System.out.println("planMapper = " + planMapper);
        assertNotNull(planMapper);
    }

    @Test
    void testSelectPlanBoardByPlanBoardId() {
        // given
        Long planBoardId = 1L;

        // when
        PlanBoardDto planBoard = planMapper.selectPlanBoardByPlanBoardId(planBoardId);

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
        List<PlanBoardDto> planBoardDtoList = planMapper.selectPlanBoardByUserId(userId);

        // then
        System.out.println(planBoardDtoList);
        assertEquals(1, planBoardDtoList.size());
        assertEquals(planBoardDtoList.get(0).getUserId(), userId);
    }

    @Test
    @Transactional
    void testInsertPlanBoardDto() {
        // given
        PlanBoardDto insertPlanBoardDto = makePlanBoardDto();
        System.out.println("insertPlanBoardDto = " + insertPlanBoardDto);
        // when
        int result = planMapper.insertPlanBoard(insertPlanBoardDto);

        // then
        PlanBoardDto selectPlanBoardDto = planMapper.selectPlanBoardByPlanBoardId(
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
            .place("test")
            .order(order)
            .date(Date.valueOf("2023-05-05"))
            .startTime(Time.valueOf("09:00:" + order))
            .endTime(Time.valueOf("09:00:" + (order + 1)))
            .build();
    }

    PlanBoardDto makePlanBoardDto() {
        return PlanBoardDto
            .builder()
            .userId("test")
            .title("test")
            .startDate(Date.valueOf("2023-05-05"))
            .endDate(Date.valueOf("2023-05-07"))
            .build();
    }
}
