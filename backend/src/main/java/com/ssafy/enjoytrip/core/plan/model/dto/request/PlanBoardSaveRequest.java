package com.ssafy.enjoytrip.core.plan.model.dto.request;


import java.util.List;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
public class PlanBoardSaveRequest {

    @NotBlank(message = "유저 아이디는 필수 입력 값입니다.")
    private String userId;

    @NotBlank(message = "제목은 필수 입력 값입니다.")
    private String title;

    @NotBlank(message = "여행 시작 날짜는 필수 입력 값입니다.")
    private String startDate;

    @NotBlank(message = "여행 종료 날짜는 필수 입력 값입니다.")
    private String endDate;

    @NotBlank(message = "여행지는 필수 입력 값입니다.")
    private List<PlanSaveRequest> planList;
}
