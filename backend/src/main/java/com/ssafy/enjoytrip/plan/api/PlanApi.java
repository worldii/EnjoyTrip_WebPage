package com.ssafy.enjoytrip.plan.api;


import com.ssafy.enjoytrip.plan.model.dto.PlanBoardRequest;
import com.ssafy.enjoytrip.plan.service.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/plan")
@RequiredArgsConstructor
public class PlanApi {
    final private PlanService planService;

    @PostMapping
    ResponseEntity<?> save(@RequestBody PlanBoardRequest planBoardRequest){
        int result = planService.savePlanBoard(planBoardRequest);

        Map<String, Object> resultMap = new HashMap<>();

        if(result>0){
            resultMap.put("success",true);
        }else{
            resultMap.put("success",false);
        }

        return new ResponseEntity<>(resultMap, HttpStatus.ACCEPTED);
    }

    @GetMapping("/list/{userId}")
    ResponseEntity<?> list(@PathVariable String userId){
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("list",planService.list(userId));
        return new ResponseEntity<>(resultMap, HttpStatus.ACCEPTED);
    }

    @GetMapping("/detail/{planBoardId}")
    ResponseEntity<?> detail(@PathVariable String planBoardId){
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("planBoard",planService.search(Integer.parseInt(planBoardId)));
        return new ResponseEntity<>(resultMap, HttpStatus.ACCEPTED);
    }
}
