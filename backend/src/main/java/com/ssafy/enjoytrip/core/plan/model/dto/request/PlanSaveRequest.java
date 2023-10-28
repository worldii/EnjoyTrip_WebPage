package com.ssafy.enjoytrip.core.plan.model.dto.request;

import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PlanSaveRequest {

    @NotBlank(message = "여행지 이름은 필수 입력 값입니다.")
    private String placeName;
    private String content;
    @NotBlank(message = "여행지 순서는 필수 입력 값입니다.")
    private int planOrder;
    @NotBlank(message = "여행지 소요시간은 필수 입력 값입니다.")
    private Long expectDuration;
    @NotBlank(message = "여행지 예상 날짜는 필수 입력 값입니다.")
    private String expectDate;
    @NotBlank(message = "여행지 예상 시간은 필수 입력 값입니다.")
    private String startTime;
    @NotBlank(message = "여행지 예상 시간은 필수 입력 값입니다.")
    private String endTime;

}
