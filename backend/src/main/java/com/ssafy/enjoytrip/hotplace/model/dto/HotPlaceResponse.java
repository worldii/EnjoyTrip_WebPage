package com.ssafy.enjoytrip.hotplace.model.dto;

import com.ssafy.enjoytrip.hotplace.model.entity.HotPlace;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HotPlaceResponse {

    private String hotPlaceId;
    private String hotPlaceName;

    private String x;
    private String y;
    private int vote;
    private String placeUrl;
    private String roadAddressName;
    private String addressName;

    public static HotPlaceResponse from(final HotPlace hotPlace) {
        return new HotPlaceResponse(
            hotPlace.getHotPlaceId(),
            hotPlace.getHotPlaceName(),
            hotPlace.getX(),
            hotPlace.getY(),
            hotPlace.getVote(),
            hotPlace.getPlaceUrl(),
            hotPlace.getRoadAddressName(),
            hotPlace.getAddressName()
        );
    }
}
