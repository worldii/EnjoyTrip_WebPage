package com.ssafy.enjoytrip.core.hotplace.model.entity;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class HotPlace {

    private String hotPlaceId;
    private String hotPlaceName;
    private String x;
    private String y;
    private Long vote;
    private String placeUrl;
    private String roadAddressName;
    private String addressName;
    private List<HotPlaceArticle> hotPlaceArticles;
}
