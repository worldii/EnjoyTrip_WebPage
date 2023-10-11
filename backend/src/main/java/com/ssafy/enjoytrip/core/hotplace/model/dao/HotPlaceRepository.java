package com.ssafy.enjoytrip.core.hotplace.model.dao;

import com.ssafy.enjoytrip.core.hotplace.model.entity.HotPlace;
import com.ssafy.enjoytrip.core.hotplace.model.mapper.HotPlaceMapper;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class HotPlaceRepository {

    private final HotPlaceMapper hotPlaceMapper;

    @Transactional
    public int insertHotPlace(final HotPlace hotPlace) {
        return hotPlaceMapper.insertHotPlace(hotPlace);
    }

    @Transactional(readOnly = true)
    public List<HotPlace> selectAllHotPlace(final String keyword) {
        return hotPlaceMapper.selectAllHotPlace(keyword);
    }

    @Transactional(readOnly = true)
    public Optional<HotPlace> selectAllByHotPlaceId(final String hotPlaceId) {
        return hotPlaceMapper.selectAllByHotPlaceId(hotPlaceId);
    }

    @Transactional(readOnly = true)
    public Optional<HotPlace> selectHotPlaceByHotPlaceId(final String hotPlaceId) {
        return hotPlaceMapper.selectHotPlaceByHotPlaceId(hotPlaceId);
    }

    @Transactional
    public void updateHotPlace(HotPlace hotPlace) {
        hotPlaceMapper.updateHotPlace(hotPlace);
    }
}
