package com.ssafy.enjoytrip.hotplace.model;

import com.ssafy.enjoytrip.hotplace.model.HotPlace.HotPlaceTag;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class HotPlaceArticle {

    private int hotPlaceArticleId;
    private String hotPlaceId;
    private String hotPlaceName;
    private String userId;
    private String content;
    private String createAt;
    private String imageUrl;
    private List<HotPlaceTag> hotPlaceTags = new ArrayList<>();
}
