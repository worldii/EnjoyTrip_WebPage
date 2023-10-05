package com.ssafy.enjoytrip.core.hotplace.service;

import com.ssafy.enjoytrip.core.hotplace.model.dto.HotPlaceArticleResponse;
import com.ssafy.enjoytrip.core.hotplace.model.dto.HotPlaceResponse;
import com.ssafy.enjoytrip.core.hotplace.model.entity.HotPlace;
import com.ssafy.enjoytrip.core.hotplace.model.entity.HotPlaceArticle;
import com.ssafy.enjoytrip.core.hotplace.model.entity.HotPlaceTag;
import java.util.List;

public interface HotPlaceService {

    List<HotPlaceResponse> selectAllHotPlace();

    List<HotPlace> selectHotPlaceByKeyword(final String keyword);

    List<HotPlaceArticleResponse> selectAllHotPlaceArticle(final String hotPlaceId);

    HotPlace selectHotPlaceByHotPlaceId(final String hotPlaceId);

    HotPlaceArticle selectHotPlaceArticleByArticleId(final int hotPlaceId);


    int updateHotPlaceArticleImage(final int hotPlaceArticleId, final String imageUrl);

    int increaseHitHotPlaceCount(String hotPlaceId);

    int decreaseHitHotPlaceCount(String hotPlaceId);

    int insertHotPlace(HotPlace hotPlace);

    int insertHotPlaceArticle(HotPlaceArticle hotPlaceArticle);

    int updateHotPlaceTag(String hotPlaceId, String tagId);

    void updateHotPlaceTagList(String hotPlaceId, List<String> tagIdList);

    int insertHotPlaceTag(String hotPlaceId, String tagName);

    void insertHotPlaceTagList(String hotPlaceId, List<String> tagIdList);

    List<HotPlaceTag> selectHotPlaceTagList(String hotPlaceId);


}