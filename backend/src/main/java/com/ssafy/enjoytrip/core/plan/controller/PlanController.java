package com.ssafy.enjoytrip.core.plan.controller;


import com.ssafy.enjoytrip.core.plan.model.dto.request.PlanBoardSaveRequest;
import com.ssafy.enjoytrip.core.plan.model.dto.response.PlanBoardResponse;
import com.ssafy.enjoytrip.core.plan.service.PlanService;
import com.ssafy.enjoytrip.global.auth.model.dto.LoginUser;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/plan")
@RequiredArgsConstructor
public class PlanController {

    private final PlanService planService;

    @PostMapping
    ResponseEntity<Long> save(final @RequestBody PlanBoardSaveRequest planBoardRequest) {
        return ResponseEntity.ok(planService.savePlanBoard(planBoardRequest));
    }

    @GetMapping
    ResponseEntity<List<PlanBoardResponse>> getPlanListByUser(@LoginUser final String userId) {
        return ResponseEntity.ok(planService.selectAll(userId));
    }

    @GetMapping("/{planBoardId}")
    ResponseEntity<PlanBoardResponse> detail(
        @PathVariable final Long planBoardId,
        @LoginUser final String userId
    ) {
        return ResponseEntity.ok(planService.detail(planBoardId, userId));
    }
}
