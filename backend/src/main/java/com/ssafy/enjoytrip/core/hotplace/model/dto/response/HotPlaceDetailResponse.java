package com.ssafy.enjoytrip.core.hotplace.model.dto.response;


import com.ssafy.enjoytrip.core.hotplace.model.entity.HotPlace;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HotPlaceDetailResponse {

    private String hotPlaceId;
    private String hotPlaceName;
    private Double x;
    private Double y;
    private Long vote;
    private String placeUrl;
    private String roadAddressName;
    private String addressName;
    private List<HotPlaceArticleResponse> hotPlaceArticles;
    private List<HotPlaceTagResponse> hotPlaceTagResponses;

    public static HotPlaceDetailResponse from(final HotPlace hotPlace) {
        final List<HotPlaceArticleResponse> hotPlaceArticles = getArticleResponses(hotPlace);

        final List<HotPlaceTagResponse> hotPlaceTagResponses = getTagResponses(hotPlace);

        return new HotPlaceDetailResponse(
                hotPlace.getHotPlaceId(),
                hotPlace.getHotPlaceName(),
                hotPlace.getX(),
                hotPlace.getY(),
                hotPlace.getVote(),
                hotPlace.getImageUrl(),
                hotPlace.getRoadAddressName(),
                hotPlace.getAddressName(),
                hotPlaceArticles,
                hotPlaceTagResponses);
    }

    private static List<HotPlaceArticleResponse> getArticleResponses(final HotPlace hotPlace) {
        if (hotPlace.getHotPlaceArticles() == null) {
            return Collections.emptyList();
        }

        return hotPlace.getHotPlaceArticles().stream()
                .map(HotPlaceArticleResponse::from)
                .collect(Collectors.toList());
    }

    private static List<HotPlaceTagResponse> getTagResponses(final HotPlace hotPlace) {
        if (hotPlace.getHotPlaceTags() == null) {
            return Collections.emptyList();
        }

        return hotPlace.getHotPlaceTags().stream()
                .map(HotPlaceTagResponse::from)
                .collect(Collectors.toList());
    }
}
