package com.ssafy.enjoytrip.core.hotplace.model.dto.request;

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

    private String hotPlaceId;
    private String hotPlaceName;
    private Double x;
    private Double y;
    private Long vote;
    private String placeUrl;
    private String roadAddressName;
    private String addressName;
}
