package com.ssafy.enjoytrip.core.hotplace.model.dto.response;


import com.ssafy.enjoytrip.core.hotplace.model.entity.HotPlaceArticle;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HotPlaceArticleResponse {

    private Long hotPlaceArticleId;
    private String hotPlaceId;
    private String hotPlaceName;
    private String userId;
    private String content;
    private String createAt;
    private List<String> imageUrl;

    public static HotPlaceArticleResponse from(final HotPlaceArticle hotPlaceArticle) {
        return new HotPlaceArticleResponse(
                hotPlaceArticle.getHotPlaceArticleId(),
                hotPlaceArticle.getHotPlaceId(),
                hotPlaceArticle.getHotPlaceName(),
                hotPlaceArticle.getUserId(),
                hotPlaceArticle.getContent(),
                hotPlaceArticle.getCreateAt(),
                hotPlaceArticle.getImageUrl());
    }
}
