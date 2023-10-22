package com.ssafy.enjoytrip.core.hotplace.model.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class HotPlaceTags {

    private final Map<String, HotPlaceTag> hotPlaceTagMap;

    public HotPlaceTags(
        final List<HotPlaceTag> tagList,
        final String hotPlaceId,
        final List<String> tagName
    ) {
        this.hotPlaceTagMap = new HashMap<>();

        for (final HotPlaceTag tag : tagList) {
            hotPlaceTagMap.put(tag.getTagName(), tag);
        }
        increaseTagCount(hotPlaceId, tagName);
    }

    // 각각의 tagname을 increase 하는 과정
    private void increaseTagCount(final String hotPlaceId, final List<String> tagName) {
        for (final String name : tagName) {
            final HotPlaceTag newHotPlaceTag = hotPlaceTagMap
                .getOrDefault(name, HotPlaceTag.of(name, hotPlaceId)).increaseTagCount();

            hotPlaceTagMap.put(name, newHotPlaceTag);
        }
    }

    // tagList를 unmodifiable하게 get함.
    public List<HotPlaceTag> getTagList() {
        return new ArrayList<>(hotPlaceTagMap.values());
    }
}
