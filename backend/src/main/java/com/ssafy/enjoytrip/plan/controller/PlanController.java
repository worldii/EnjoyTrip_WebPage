package com.ssafy.enjoytrip.plan.controller;


import com.ssafy.enjoytrip.plan.model.dto.PlanBoardRequest;
import com.ssafy.enjoytrip.plan.service.PlanService;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    ResponseEntity<?> save(@RequestBody PlanBoardRequest planBoardRequest) {
        int result = planService.savePlanBoard(planBoardRequest);

        Map<String, Object> resultMap = new HashMap<>();

        if (result > 0) {
            resultMap.put("success", true);
        } else {
            resultMap.put("success", false);
        }

        return new ResponseEntity<>(resultMap, HttpStatus.ACCEPTED);
    }

    @GetMapping("/list/{userId}")
    ResponseEntity<?> list(@PathVariable String userId) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("list", planService.list(userId));
        return new ResponseEntity<>(resultMap, HttpStatus.ACCEPTED);
    }

    @GetMapping("/detail/{planBoardId}")
    ResponseEntity<?> detail(@PathVariable String planBoardId) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("planBoard", planService.search(Integer.parseInt(planBoardId)));
        return new ResponseEntity<>(resultMap, HttpStatus.ACCEPTED);
    }
}
