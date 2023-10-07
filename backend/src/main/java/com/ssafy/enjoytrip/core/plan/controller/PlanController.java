package com.ssafy.enjoytrip.core.plan.controller;


import com.ssafy.enjoytrip.core.plan.model.dto.PlanBoardDto;
import com.ssafy.enjoytrip.core.plan.model.dto.request.PlanBoardRequest;
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
    ResponseEntity<Integer> save(final @RequestBody PlanBoardRequest planBoardRequest) {
        return ResponseEntity.ok(planService.savePlanBoard(planBoardRequest));
    }

    @GetMapping
    ResponseEntity<List<PlanBoardDto>> getPlanListByUser(
        @LoginUser final String userId
    ) {
        return ResponseEntity.ok(planService.list(userId));
    }

    @GetMapping("/{planBoardId}")
    ResponseEntity<PlanBoardResponse> detail(
        @PathVariable final Long planBoardId,
        @LoginUser final String userId
    ) {
        PlanBoardResponse search = planService.detail(planBoardId, userId);
        return ResponseEntity.ok(search);
    }
}
