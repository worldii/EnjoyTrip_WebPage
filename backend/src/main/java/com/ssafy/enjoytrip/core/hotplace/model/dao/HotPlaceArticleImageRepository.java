package com.ssafy.enjoytrip.core.hotplace.model.dao;

import com.ssafy.enjoytrip.core.hotplace.model.entity.HotPlaceArticleImage;
import com.ssafy.enjoytrip.core.hotplace.model.mapper.HotPlaceArticleImageMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HotPlaceArticleImageRepository {

    private final HotPlaceArticleImageMapper hotPlaceArticleImageMapper;

    @Transactional
    public void insertFile(final List<HotPlaceArticleImage> imageFiles) {
        hotPlaceArticleImageMapper.insertFile(imageFiles);
    }

    public List<HotPlaceArticleImage> selectFile(final Long hotPlaceArticleId) {
        return hotPlaceArticleImageMapper.selectFile(hotPlaceArticleId);
    }

    public void deleteFileByHotPlaceArticleId(final Long hotPlaceArticleId) {
        hotPlaceArticleImageMapper.deleteFileByHotPlaceArticleId(hotPlaceArticleId);
    }
}
