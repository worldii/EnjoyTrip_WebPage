package com.ssafy.enjoytrip.core.hotplace.model.dao;

import com.ssafy.enjoytrip.core.hotplace.model.entity.HotPlaceTag;
import com.ssafy.enjoytrip.core.hotplace.model.mapper.HotPlaceTagMapper;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class HotPlaceTagRepository {

    private final HotPlaceTagMapper hotPlaceTagMapper;

    @Transactional
    public void insertTags(final List<HotPlaceTag> tags) {
        hotPlaceTagMapper.insertTags(tags);
    }

    @Transactional
    public int increaseTagCount(final Long hotPlaceTagId) {
        return hotPlaceTagMapper.increaseTagCount(hotPlaceTagId);
    }

    @Transactional(readOnly = true)
    public Optional<HotPlaceTag> findById(final Long hotPlaceTagId) {
        return hotPlaceTagMapper.findById(hotPlaceTagId);
    }
}
