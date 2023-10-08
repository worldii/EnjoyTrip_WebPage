package com.ssafy.enjoytrip.core.hotplace.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ssafy.enjoytrip.core.hotplace.model.dao.HotPlaceRepository;
import com.ssafy.enjoytrip.core.hotplace.model.dto.HotPlaceResponse;
import com.ssafy.enjoytrip.core.hotplace.model.entity.HotPlace;
import com.ssafy.enjoytrip.core.hotplace.model.entity.HotPlaceArticle;
import com.ssafy.enjoytrip.core.hotplace.model.entity.HotPlaceTag;
import com.ssafy.enjoytrip.global.error.HotPlaceException;
import com.ssafy.enjoytrip.global.error.PageInfoRequest;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class HotPlaceServiceImpl implements HotPlaceService {

    private final HotPlaceRepository hotPlaceRepository;

    @Override
    public List<HotPlaceResponse> selectAllHotPlace(
        final PageInfoRequest pageInfoRequest, final String keyword) {
        PageHelper.startPage(pageInfoRequest.getPage(), pageInfoRequest.getPageSize());
        final Page<HotPlace> hotPlaces = hotPlaceRepository.selectAllHotPlace(keyword);

        return hotPlaces.stream()
            .map(HotPlaceResponse::from)
            .collect(Collectors.toList());
    }

    @Override
    public HotPlace selectHotPlaceByHotPlaceId(final String hotPlaceId) {
        return hotPlaceRepository.selectAllByHotPlaceId(hotPlaceId)
            .orElseThrow(() -> new HotPlaceException("존재하지 않는 핫플레이스입니다."));
    }


    @Override
    public int updateHotPlaceArticleImage(final int hotPlaceArticleId, final String imageUrl) {
        return hotPlaceRepository.updateHotPlaceArticleImage(hotPlaceArticleId, imageUrl);
    }

    @Override
    public int increaseHitHotPlaceCount(final String hotPlaceId) {
        return hotPlaceRepository.increaseHitHotPlaceCount(hotPlaceId);
    }

    @Override
    public int decreaseHitHotPlaceCount(final String hotPlaceId) {
        return hotPlaceRepository.decreaseHitHotPlaceCount(hotPlaceId);
    }

    @Override
    public int insertHotPlace(final HotPlace hotPlace) {
        return hotPlaceRepository.insertHotPlace(hotPlace);
    }

    @Override
    public Long insertHotPlaceArticle(final HotPlaceArticle hotPlaceArticle) {
        hotPlaceRepository.insertHotPlaceArticle(hotPlaceArticle);
        return hotPlaceArticle.getHotPlaceArticleId();
    }

    @Override
    public int updateHotPlaceTag(final String hotPlaceId, final String tagName) {
        return hotPlaceRepository.updateHotPlaceTag(hotPlaceId, tagName);
    }

    @Override
    public void updateHotPlaceTagList(final String hotPlaceId, final List<String> tagIdList) {
        tagIdList.forEach(tagId -> updateHotPlaceTag(hotPlaceId, tagId));
    }

    @Override
    public int insertHotPlaceTag(final String hotPlaceId, final String tagName) {
        return hotPlaceRepository.insertHotPlaceTag(hotPlaceId, tagName);
    }

    @Override
    public void insertHotPlaceTagList(final String hotPlaceId, final List<String> tagIdList) {
        tagIdList.forEach(tagId -> insertHotPlaceTag(hotPlaceId, tagId));
    }

    @Override
    public List<HotPlaceTag> selectHotPlaceTagList(final String hotPlaceId) {
        return hotPlaceRepository.selectHotPlaceTagList(hotPlaceId);
    }
}
