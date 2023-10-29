package com.ssafy.enjoytrip.core.plan.model.dto.response;


import com.ssafy.enjoytrip.core.plan.model.entity.PlanBoard;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class PlanBoardResponse {

    private Long planBoardId;
    private String userId;
    private String title;
    private String startDate;
    private String endDate;
    private List<PlanResponse> planList;

    public static PlanBoardResponse of(final PlanBoard planBoard, final List<PlanResponse> plans) {
        return PlanBoardResponse.builder()
                .planBoardId(planBoard.getPlanBoardId())
                .userId(planBoard.getUserId())
                .title(planBoard.getTitle())
                .startDate(planBoard.getStartDate().toString())
                .endDate(planBoard.getEndDate().toString())
                .planList(plans)
                .build();
    }

    public static PlanBoardResponse from(final PlanBoard planBoard) {
        return PlanBoardResponse.builder()
                .planBoardId(planBoard.getPlanBoardId())
                .userId(planBoard.getUserId())
                .title(planBoard.getTitle())
                .startDate(planBoard.getStartDate().toString())
                .endDate(planBoard.getEndDate().toString())
                .build();
    }
}
