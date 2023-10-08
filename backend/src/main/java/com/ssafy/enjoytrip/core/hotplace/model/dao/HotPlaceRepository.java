package com.ssafy.enjoytrip.core.hotplace.model.dao;

import com.github.pagehelper.Page;
import com.ssafy.enjoytrip.core.hotplace.model.entity.HotPlace;
import com.ssafy.enjoytrip.core.hotplace.model.entity.HotPlaceArticle;
import com.ssafy.enjoytrip.core.hotplace.model.entity.HotPlaceTag;
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

    @Transactional(readOnly = true)
    public Page<HotPlace> selectAllHotPlace(final String keyword) {
        return hotPlaceMapper.selectAllHotPlace(keyword);
    }


    @Transactional(readOnly = true)
    public Optional<HotPlace> selectAllByHotPlaceId(final String hotPlaceId) {
        return hotPlaceMapper.selectAllByHotPlaceId(hotPlaceId);
    }

    @Transactional
    public int insertHotPlace(final HotPlace hotPlace) {
        return hotPlaceMapper.insertHotPlace(hotPlace);
    }

    @Transactional
    public int insertHotPlaceArticle(final HotPlaceArticle hotPlaceArticle) {
        return hotPlaceMapper.insertHotPlaceArticle(hotPlaceArticle);
    }

    @Transactional
    public int updateHotPlaceArticleImage(final int hotPlaceArticleId, String imageUrl) {
        return hotPlaceMapper.updateHotPlaceArticleImage(hotPlaceArticleId, imageUrl);
    }

    @Transactional
    public int increaseHitHotPlaceCount(final String hotPlaceId) {
        return hotPlaceMapper.increaseHitHotPlaceCount(hotPlaceId);
    }

    @Transactional
    public int decreaseHitHotPlaceCount(final String hotPlaceId) {
        return hotPlaceMapper.decreaseHitHotPlaceCount(hotPlaceId);
    }

    @Transactional
    public int updateHotPlaceTag(final String hotPlaceId, final String tagName) {
        return hotPlaceMapper.updateHotPlaceTag(hotPlaceId, tagName);
    }

    @Transactional
    public int insertHotPlaceTag(final String hotPlaceId, final String tagName) {
        return hotPlaceMapper.insertHotPlaceTag(hotPlaceId, tagName);
    }

    @Transactional(readOnly = true)
    public List<HotPlaceTag> selectHotPlaceTagList(final String hotPlaceId) {
        return hotPlaceMapper.selectHotPlaceTagList(hotPlaceId);
    }

    @Transactional(readOnly = true)
    public List<HotPlace> selectHotPlaceByKeyword(final String keyword) {
        return hotPlaceMapper.selectHotPlaceByKeyword(keyword);
    }
}
