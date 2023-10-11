package com.ssafy.enjoytrip.core.hotplace.model.entity;

import com.ssafy.enjoytrip.global.error.HotPlaceException;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class HotPlace {

    private String hotPlaceId;
    private String hotPlaceName;
    private Double x;
    private Double y;
    private Long vote;
    private String placeUrl;
    private String roadAddressName;
    private String addressName;
    private List<HotPlaceArticle> hotPlaceArticles;
    private List<HotPlaceTag> hotPlaceTags;

    @Builder
    public HotPlace(
        final String hotPlaceId,
        final String hotPlaceName,
        final Double x, final Double y,
        final Long vote, final String placeUrl,
        final String roadAddressName, final String addressName
    ) {
        validateHotPlaceId(hotPlaceId);
        validateHotPlaceName(hotPlaceName);
        validateX(x);
        validateY(y);
        validatePlaceUrl(placeUrl);
        validateRoadAddressName(roadAddressName);
        validateAddressName(addressName);

        this.hotPlaceId = hotPlaceId;
        this.hotPlaceName = hotPlaceName;
        this.x = x;
        this.y = y;
        this.vote = vote;
        this.placeUrl = placeUrl;
        this.roadAddressName = roadAddressName;
        this.addressName = addressName;
    }

    private void validateHotPlaceId(final String hotPlaceId) {
        if (hotPlaceId == null || hotPlaceId.isEmpty()) {
            throw new HotPlaceException("핫플레이스 아이디가 없습니다.");
        }
    }

    private void validateHotPlaceName(final String hotPlaceName) {
        if (hotPlaceName == null || hotPlaceName.isEmpty()) {
            throw new HotPlaceException("핫플레이스 이름이 없습니다.");
        }
    }


    private void validateX(final Double x) {
        if (x == null) {
            throw new HotPlaceException("x좌표가 없습니다.");
        }
    }

    private void validateY(final Double y) {
        if (y == null) {
            throw new HotPlaceException("y좌표가 없습니다.");
        }
    }


    private void validateAddressName(final String addressName) {
        if (addressName == null || addressName.isEmpty()) {
            throw new HotPlaceException("주소가 없습니다.");
        }
    }

    private void validateRoadAddressName(final String roadAddressName) {
        if (roadAddressName == null || roadAddressName.isEmpty()) {
            throw new HotPlaceException("도로명 주소가 없습니다.");
        }
    }

    private void validatePlaceUrl(final String placeUrl) {
        if (placeUrl == null || placeUrl.isEmpty()) {
            throw new HotPlaceException("핫플레이스 URL이 없습니다.");
        }
    }

    public void updateVoteCount(final Long voteCount) {
        this.vote = voteCount;
    }
}
