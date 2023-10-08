package com.ssafy.enjoytrip.core.hotplace.service;

import com.ssafy.enjoytrip.core.hotplace.model.dto.HotPlaceResponse;
import com.ssafy.enjoytrip.core.hotplace.model.entity.HotPlace;
import com.ssafy.enjoytrip.core.hotplace.model.entity.HotPlaceArticle;
import com.ssafy.enjoytrip.core.hotplace.model.entity.HotPlaceTag;
import com.ssafy.enjoytrip.global.error.PageInfoRequest;
import java.util.List;

public interface HotPlaceService {

    HotPlace selectHotPlaceByHotPlaceId(final String hotPlaceId);

    List<HotPlaceResponse> selectAllHotPlace(
        final PageInfoRequest pageInfoRequest, final String keyword);
    
    int updateHotPlaceArticleImage(final int hotPlaceArticleId, final String imageUrl);

    int increaseHitHotPlaceCount(String hotPlaceId);

    int decreaseHitHotPlaceCount(String hotPlaceId);

    int insertHotPlace(HotPlace hotPlace);

    Long insertHotPlaceArticle(HotPlaceArticle hotPlaceArticle);

    int updateHotPlaceTag(String hotPlaceId, String tagId);

    void updateHotPlaceTagList(String hotPlaceId, List<String> tagIdList);

    int insertHotPlaceTag(String hotPlaceId, String tagName);

    void insertHotPlaceTagList(String hotPlaceId, List<String> tagIdList);

    List<HotPlaceTag> selectHotPlaceTagList(String hotPlaceId);


}
