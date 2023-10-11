package com.ssafy.enjoytrip.core.hotplace.model.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class HotPlaceTag {

    private Long hotPlaceTagId;
    private String tagName;
    private String hotPlaceId;
    private Long count = 1L;

    @Builder
    public HotPlaceTag(
        final Long hotPlaceTagId,
        final String tagName,
        final String hotPlaceId,
        final Long count
    ) {
        this.hotPlaceTagId = hotPlaceTagId;
        this.tagName = tagName;
        this.hotPlaceId = hotPlaceId;
        this.count = count;
    }

    public static HotPlaceTag of(final String name, final String hotPlaceId) {
        return HotPlaceTag.builder()
            .tagName(name)
            .hotPlaceId(hotPlaceId)
            .build();
    }

    public void increaseTagCount() {
        this.count++;
    }
}
