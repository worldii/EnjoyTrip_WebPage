package com.ssafy.enjoytrip.core.hotplace.model.dto.response;

import com.ssafy.enjoytrip.core.hotplace.model.entity.HotPlaceTag;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class HotPlaceTagResponse {

    private String tagName;
    private String hotPlaceId;
    private Long count;

    public static HotPlaceTagResponse from(final HotPlaceTag hotPlaceTag) {
        return HotPlaceTagResponse.builder()
            .tagName(hotPlaceTag.getTagName())
            .hotPlaceId(hotPlaceTag.getHotPlaceId())
            .count(hotPlaceTag.getCount())
            .build();
    }
}
