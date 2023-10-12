package com.ssafy.enjoytrip.core.hotplace.model.dao;

import com.ssafy.enjoytrip.core.hotplace.model.entity.HotPlaceImageInfo;
import com.ssafy.enjoytrip.core.hotplace.model.mapper.HotPlaceImageMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class HotPlaceImageInfoRepository {

    private final HotPlaceImageMapper hotPlaceImageMapper;

    @Transactional
    public void insertFile(
        final String hotPlaceId,
        final List<HotPlaceImageInfo> imageFiles
    ) {
        hotPlaceImageMapper.insertFile(hotPlaceId, imageFiles);
    }

    @Transactional(readOnly = true)
    public List<HotPlaceImageInfo> selectFile(final String hotPlaceId) {
        return hotPlaceImageMapper.selectFile(hotPlaceId);
    }

    @Transactional
    public void deleteFileByHotPlaceId(final String hotPlaceId) {
        hotPlaceImageMapper.deleteFileByHotPlaceId(hotPlaceId);
    }
}
