package com.ssafy.enjoytrip.hotplace.service;

import com.ssafy.enjoytrip.hotplace.model.dto.HotPlaceArticleResponse;
import com.ssafy.enjoytrip.hotplace.model.dto.HotPlaceResponse;
import com.ssafy.enjoytrip.hotplace.model.entity.HotPlace;
import com.ssafy.enjoytrip.hotplace.model.entity.HotPlaceArticle;
import com.ssafy.enjoytrip.hotplace.model.entity.HotPlaceTag;
import com.ssafy.enjoytrip.hotplace.model.mapper.HotPlaceMapper;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class HotPlaceServiceImpl implements HotPlaceService {

    private final HotPlaceMapper hotPlaceMapper;

    @Override
    public List<HotPlaceResponse> selectAllHotPlace() {
        final List<HotPlace> hotPlaces = hotPlaceMapper.selectAllHotPlace();
        return hotPlaces.stream()
            .map(HotPlaceResponse::from)
            .collect(Collectors.toList());
    }

    @Override
    public List<HotPlaceArticleResponse> selectAllHotPlaceArticle(final String hotPlaceId) {
        List<HotPlaceArticle> hotPlaceArticles = hotPlaceMapper.selectAllHotPlaceArticle(
            hotPlaceId);
        return hotPlaceArticles.stream()
            .map(HotPlaceArticleResponse::from)
            .collect(Collectors.toList());
    }

    @Override
    public HotPlace selectHotPlaceByHotPlaceId(final String hotPlaceId) {
        return hotPlaceMapper.selectHotPlaceByHotPlaceId(hotPlaceId)
            .orElseThrow(() -> new RuntimeException("존재하지 않는 핫플레이스입니다."));
    }

    @Override
    public HotPlaceArticle selectHotPlaceArticleByArticleId(final int hotPlaceArticleId) {
        return hotPlaceMapper
            .selectHotPlaceArticleByArticleId(hotPlaceArticleId)
            .orElseThrow(() -> new RuntimeException("존재하지 않는 핫플레이스입니다."));
    }

    @Override
    public int updateHotPlaceArticleImage(final int hotPlaceArticleId, final String imageUrl) {
        return hotPlaceMapper.updateHotPlaceArticleImage(hotPlaceArticleId, imageUrl);
    }

    @Override
    public int increaseHitHotPlaceCount(final String hotPlaceId) {
        return hotPlaceMapper.increaseHitHotPlaceCount(hotPlaceId);
    }

    @Override
    public int decreaseHitHotPlaceCount(final String hotPlaceId) {
        return hotPlaceMapper.decreaseHitHotPlaceCount(hotPlaceId);
    }

    @Override
    public int insertHotPlace(final HotPlace hotPlace) {
        return hotPlaceMapper.insertHotPlace(hotPlace);
    }

    @Override
    public int insertHotPlaceArticle(final HotPlaceArticle hotPlaceArticle) {
        hotPlaceMapper.insertHotPlaceArticle(hotPlaceArticle);
        return hotPlaceArticle.getHotPlaceArticleId();
    }

    @Override
    public int updateHotPlaceTag(final String hotPlaceId, final String tagName) {
        return hotPlaceMapper.updateHotPlaceTag(hotPlaceId, tagName);
    }

    @Override
    public void updateHotPlaceTagList(final String hotPlaceId, final List<String> tagIdList) {
        tagIdList.forEach(tagId -> updateHotPlaceTag(hotPlaceId, tagId));
    }

    @Override
    public int insertHotPlaceTag(final String hotPlaceId, final String tagName) {
        return hotPlaceMapper.insertHotPlaceTag(hotPlaceId, tagName);
    }

    @Override
    public void insertHotPlaceTagList(final String hotPlaceId, final List<String> tagIdList) {
        tagIdList.forEach(tagId -> insertHotPlaceTag(hotPlaceId, tagId));
    }

    @Override
    public List<HotPlaceTag> selectHotPlaceTagList(final String hotPlaceId) {
        return hotPlaceMapper.selectHotPlaceTagList(hotPlaceId);
    }

    @Override
    public List<HotPlace> selectHotPlaceByKeyword(final String keyword) {
        return hotPlaceMapper.selectHotPlaceByKeyword(keyword);
    }

}
