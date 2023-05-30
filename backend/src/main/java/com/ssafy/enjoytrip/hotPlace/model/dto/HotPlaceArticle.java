package com.ssafy.enjoytrip.hotPlace.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class HotPlaceArticle {
    private int hotPlaceArticleId;
    private String userId;
    private String hotPlaceId;
    private String content;
    private String createAt;
    private String hotPlaceName;
    private String imageUrl;
}
