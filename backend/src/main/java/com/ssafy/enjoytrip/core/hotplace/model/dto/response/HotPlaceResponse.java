package com.ssafy.enjoytrip.core.hotplace.model.dto.response;


import com.ssafy.enjoytrip.core.hotplace.model.entity.HotPlace;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HotPlaceResponse {

    private String hotPlaceId;
    private String hotPlaceName;
    private String imageUrl;

    public static HotPlaceResponse from(final HotPlace hotPlace) {
        return new HotPlaceResponse(
                hotPlace.getHotPlaceId(), hotPlace.getHotPlaceName(), hotPlace.getImageUrl());
    }
}
