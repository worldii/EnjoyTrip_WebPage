package com.ssafy.enjoytrip.core.hotplace.model.dao;

import com.ssafy.enjoytrip.core.hotplace.model.mapper.HotPlaceTagMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class HotPlaceTagRepository {

    private final HotPlaceTagMapper hotPlaceTagMapper;

    @Transactional
    public int updateHotPlaceTag(final String hotPlaceId, final String tagName) {
        return hotPlaceTagMapper.updateHotPlaceTag(hotPlaceId, tagName);
    }

    @Transactional
    public int insertHotPlaceTag(final String hotPlaceId, final String tagName) {
        return hotPlaceTagMapper.insertHotPlaceTag(hotPlaceId, tagName);
    }

    @Transactional(readOnly = true)
    public Long selectHotPlaceTagIdByTagNameAndHotPlaceId(
        final String tagName,
        final String hotPlaceId
    ) {
        return hotPlaceTagMapper.selectHotPlaceTagIdByTagNameAndHotPlaceId(tagName, hotPlaceId);
    }
}
