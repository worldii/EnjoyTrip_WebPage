package com.ssafy.enjoytrip.hotplace.model.dto;

import com.ssafy.enjoytrip.hotplace.model.entity.HotPlaceArticle;
import com.ssafy.enjoytrip.hotplace.model.entity.HotPlaceTag;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HotPlaceArticleResponse {

    private int hotPlaceArticleId;
    private String hotPlaceId;
    private String hotPlaceName;
    private String userId;
    private String content;
    private String createAt;
    private String imageUrl;
    private List<HotPlaceTag> hotPlaceTags;

    public static HotPlaceArticleResponse from(final HotPlaceArticle hotPlaceArticle) {
        return new HotPlaceArticleResponse(
            hotPlaceArticle.getHotPlaceArticleId(),
            hotPlaceArticle.getHotPlaceId(),
            hotPlaceArticle.getHotPlaceName(),
            hotPlaceArticle.getUserId(),
            hotPlaceArticle.getContent(),
            hotPlaceArticle.getCreateAt(),
            hotPlaceArticle.getImageUrl(),
            hotPlaceArticle.getHotPlaceTags()
        );
    }
}
