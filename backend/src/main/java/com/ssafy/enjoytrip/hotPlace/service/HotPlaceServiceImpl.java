package com.ssafy.enjoytrip.hotPlace.service;

import com.ssafy.enjoytrip.hotPlace.model.dto.HotPlace;
import com.ssafy.enjoytrip.hotPlace.model.dto.HotPlaceArticle;
import com.ssafy.enjoytrip.hotPlace.model.dto.HotPlaceTag;
import com.ssafy.enjoytrip.hotPlace.model.mapper.HotPlaceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class HotPlaceServiceImpl implements HotPlaceService {
    private final HotPlaceMapper hotPlaceMapper;

    @Override
    public List<HotPlace> selectAllHotPlace() {
        return hotPlaceMapper.selectAllHotPlace();
    }
    @Override
    public List<HotPlaceArticle> selectAllHotPlaceArticle(String hotPlaceId) {
        return hotPlaceMapper.selectAllHotPlaceArticle(hotPlaceId);
    }
    @Override
    public HotPlace selectHotPlaceByHotPlaceId(String hotPlaceId) {
        return hotPlaceMapper.selectHotPlaceByHotPlaceId(hotPlaceId);
    }
    @Override
    public HotPlaceArticle selectHotPlaceArticleByArticleId(int hotPlaceArticleId) {
        return hotPlaceMapper.selectHotPlaceArticleByArticleId(hotPlaceArticleId);
    }
    @Override
    public int updateHotPlaceArticleImage(int hotPlaceArticleId, String imageUrl) {
        return hotPlaceMapper.updateHotPlaceArticleImage(hotPlaceArticleId, imageUrl);
    }

    @Override
    public int increaseHitHotPlaceCount(String hotPlaceId) {
        return hotPlaceMapper.increaseHitHotPlaceCount(hotPlaceId);
    }
    @Override
    public int decreaseHitHotPlaceCount(String hotPlaceId) {
        return hotPlaceMapper.decreaseHitHotPlaceCount(hotPlaceId);
    }

    @Override
    public int insertHotPlace(HotPlace hotPlace) {
        return hotPlaceMapper.insertHotPlace(hotPlace);
    }
    @Override
    public int insertHotPlaceArticle(HotPlaceArticle hotPlaceArticle) {
        hotPlaceMapper.insertHotPlaceArticle(hotPlaceArticle);
        return hotPlaceArticle.getHotPlaceArticleId();
    }

    @Override
    public int updateHotPlaceTag(String hotPlaceId, String tagName) {
        return hotPlaceMapper.updateHotPlaceTag(hotPlaceId, tagName);
    }
    @Override
    public void updateHotPlaceTagList(String hotPlaceId, List<String> tagIdList) {
        tagIdList.forEach(tagId -> updateHotPlaceTag(hotPlaceId, tagId));
    }

    @Override
    public int insertHotPlaceTag(String hotPlaceId, String tagName) {
        return hotPlaceMapper.insertHotPlaceTag(hotPlaceId, tagName);
    }
    @Override
    public void insertHotPlaceTagList(String hotPlaceId, List<String> tagIdList) {
        tagIdList.forEach(tagId -> insertHotPlaceTag(hotPlaceId, tagId));
    }
    @Override
    public List<HotPlaceTag> selectHotPlaceTagList(String hotPlaceId) {
        return hotPlaceMapper.selectHotPlaceTagList(hotPlaceId);
    }

    @Override
    public List<HotPlace> selectHotPlaceByKeyword(String keyword) {
        return hotPlaceMapper.selectHotPlaceByKeyword(keyword);
    }

}
