package com.ssafy.enjoytrip.hotPlace.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class HotPlace {
    private String hotPlaceId;
    private String hotPlaceName;
    private String x;
    private String y;
    private int vote;
    private String placeUrl;
    private String roadAddressName;
    private String addressName;
}
