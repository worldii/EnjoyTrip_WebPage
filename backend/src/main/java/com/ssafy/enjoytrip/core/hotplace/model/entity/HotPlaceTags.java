package com.ssafy.enjoytrip.core.hotplace.model.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HotPlaceTags {

    private final Map<String, HotPlaceTag> hotPlaceTagMap;

    public HotPlaceTags(final Map<String, HotPlaceTag> hotPlaceTagMap) {
        this.hotPlaceTagMap = hotPlaceTagMap;
    }

    public HotPlaceTags(
        final List<HotPlaceTag> tagList,
        final String hotPlaceId,
        final List<String> tagName
    ) {
        this.hotPlaceTagMap = new HashMap<>();
        tagList.forEach(tag -> hotPlaceTagMap.put(tag.getTagName(), tag));
        increaseTagCount(hotPlaceId, tagName);
    }

    private void increaseTagCount(final String hotPlaceId, final List<String> tagName) {
        for (String name : tagName) {
            if (hotPlaceTagMap.containsKey(name)) {
                hotPlaceTagMap.get(name).increaseTagCount();
            } else {
                hotPlaceTagMap.put(name, HotPlaceTag.of(name, hotPlaceId));
            }
        }
    }

    public List<HotPlaceTag> getTagList() {
        return new ArrayList<>(hotPlaceTagMap.values());
    }
}
