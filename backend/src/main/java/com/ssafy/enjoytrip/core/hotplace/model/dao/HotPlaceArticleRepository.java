package com.ssafy.enjoytrip.core.hotplace.model.dao;

import com.ssafy.enjoytrip.core.hotplace.model.entity.HotPlaceArticle;
import com.ssafy.enjoytrip.core.hotplace.model.mapper.HotPlaceArticleMapper;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class HotPlaceArticleRepository {

    private final HotPlaceArticleMapper hotPlaceArticleMapper;

    @Transactional
    public Long insertHotPlaceArticle(final HotPlaceArticle hotPlaceArticle) {
        return hotPlaceArticleMapper.insertHotPlaceArticle(hotPlaceArticle);
    }

    @Transactional(readOnly = true)
    public Optional<HotPlaceArticle> selectHotPlaceArticleByArticleId(
        final Long hotPlaceArticleId
    ) {
        return hotPlaceArticleMapper.selectHotPlaceArticleByArticleId(hotPlaceArticleId);
    }

    @Transactional
    public int updateHotPlaceArticleImage(final int hotPlaceArticleId, String imageUrl) {
        return hotPlaceArticleMapper.updateHotPlaceArticleImage(hotPlaceArticleId, imageUrl);
    }
}
