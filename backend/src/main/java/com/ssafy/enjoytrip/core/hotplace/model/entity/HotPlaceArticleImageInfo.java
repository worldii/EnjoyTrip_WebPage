package com.ssafy.enjoytrip.core.hotplace.model.entity;

import com.ssafy.enjoytrip.global.error.HotPlaceException;
import lombok.Builder;
import lombok.Getter;

@Getter
public class HotPlaceArticleImageInfo {

    private final Long imageInfoId;
    private final Long hotPlaceArticleId;
    private final String imageUrl;

    @Builder
    public HotPlaceArticleImageInfo(
        final Long imageInfoId,
        final String imageUrl,
        final Long hotPlaceArticleId
    ) {
        validateImageUrl(imageUrl);
        validateHotPlaceArticleId(hotPlaceArticleId);
        this.imageInfoId = imageInfoId;
        this.imageUrl = imageUrl;
        this.hotPlaceArticleId = hotPlaceArticleId;
    }

    private void validateHotPlaceArticleId(final Long hotPlaceArticleId) {
        if (hotPlaceArticleId == null) {
            throw new HotPlaceException("hotPlaceArticleId는 null이 될 수 없습니다.");
        }
    }

    private void validateImageUrl(final String imageUrl) {
        if (imageUrl == null || imageUrl.isEmpty()) {
            throw new HotPlaceException("imageUrl는 null이거나 비어있을 수 없습니다.");
        }
    }
}
