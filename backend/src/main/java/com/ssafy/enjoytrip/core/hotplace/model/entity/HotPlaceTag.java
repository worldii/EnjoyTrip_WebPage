package com.ssafy.enjoytrip.core.hotplace.model.entity;


import lombok.Builder;
import lombok.Getter;

@Getter
public class HotPlaceTag {

    private final Long hotPlaceTagId;
    private final String tagName;
    private final String hotPlaceId;
    private final Long count;

    @Builder
    public HotPlaceTag(
            final Long hotPlaceTagId,
            final String tagName,
            final String hotPlaceId,
            final Long count) {
        this.hotPlaceTagId = hotPlaceTagId;
        this.tagName = tagName;
        this.hotPlaceId = hotPlaceId;
        this.count = count;
    }

    public static HotPlaceTag of(final String name, final String hotPlaceId) {
        return HotPlaceTag.builder().tagName(name).count(0L).hotPlaceId(hotPlaceId).build();
    }

    public HotPlaceTag increaseTagCount() {
        return new HotPlaceTag(this.hotPlaceTagId, this.tagName, this.hotPlaceId, this.count + 1);
    }
}
