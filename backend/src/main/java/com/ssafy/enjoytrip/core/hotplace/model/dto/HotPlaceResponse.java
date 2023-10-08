package com.ssafy.enjoytrip.core.hotplace.model.dto;

import com.ssafy.enjoytrip.core.hotplace.model.entity.HotPlace;
import java.util.List;
import java.util.stream.Collectors;
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
    private Long vote;
    private String placeUrl;
    private String roadAddressName;
    private String addressName;
    private List<HotPlaceArticleResponse> hotPlaceArticles;

    public static HotPlaceResponse from(final HotPlace hotPlace) {
        final List<HotPlaceArticleResponse> hotPlaceArticles = hotPlace.getHotPlaceArticles()
            .stream()
            .map(HotPlaceArticleResponse::from)
            .collect(Collectors.toList());
        
        return new HotPlaceResponse(
            hotPlace.getHotPlaceId(),
            hotPlace.getHotPlaceName(),
            hotPlace.getX(),
            hotPlace.getY(),
            hotPlace.getVote(),
            hotPlace.getPlaceUrl(),
            hotPlace.getRoadAddressName(),
            hotPlace.getAddressName(),
            hotPlaceArticles);
    }
}
