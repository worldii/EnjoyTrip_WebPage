package com.ssafy.enjoytrip.core.hotplace.model.dto.request;

import javax.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class HotPlaceSaveRequest {

    @NotBlank(message = "핫플레이스 아이디는 필수 입력 값입니다.")
    private String hotPlaceId;
    @NotBlank(message = "핫플레이스 이름은 필수 입력 값입니다.")
    private String hotPlaceName;
    private Double x;
    private Double y;
    private Long vote;
    private String imageUrl;
    private String roadAddressName;
    private String addressName;
}
