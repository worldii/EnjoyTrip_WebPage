package com.ssafy.enjoytrip.core.hotplace.model.entity;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class HotPlaceArticle {

    private Long hotPlaceArticleId;
    private String hotPlaceId;
    private String hotPlaceName;
    private String userId;
    private String content;
    private String createAt;
    private String imageUrl;
    private List<HotPlaceTag> hotPlaceTags;
}
