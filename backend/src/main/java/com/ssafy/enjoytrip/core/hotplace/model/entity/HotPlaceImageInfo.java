package com.ssafy.enjoytrip.core.hotplace.model.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
public class HotPlaceImageInfo {

    private final Long hotPlaceImageInfoId;
    private final String hotPlaceId;
    private final String imageUrl;

    @Builder
    public HotPlaceImageInfo(
        final Long imageInfoId,
        final String hotPlaceId,
        final String imageUrl
    ) {
        validateHotPlaceId(hotPlaceId);
        validateImageUrl(imageUrl);
        this.hotPlaceImageInfoId = imageInfoId;
        this.hotPlaceId = hotPlaceId;
        this.imageUrl = imageUrl;
    }

    private void validateHotPlaceId(final String hotPlaceId) {
        if (hotPlaceId == null || hotPlaceId.isEmpty()) {
            throw new IllegalArgumentException("hotPlaceId는 null이거나 비어있을 수 없습니다.");
        }
    }

    private void validateImageUrl(final String imageUrl) {
        if (imageUrl == null || imageUrl.isEmpty()) {
            throw new IllegalArgumentException("imageUrl는 null이거나 비어있을 수 없습니다.");
        }
    }

    public static HotPlaceImageInfo of(final String hotPlaceId, final String imageUrl) {
        return HotPlaceImageInfo.builder()
            .hotPlaceId(hotPlaceId)
            .imageUrl(imageUrl)
            .build();
    }
}
