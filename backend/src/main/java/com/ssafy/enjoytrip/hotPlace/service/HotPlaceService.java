package com.ssafy.enjoytrip.hotPlace.service;

import com.ssafy.enjoytrip.hotPlace.model.dto.HotPlace;
import com.ssafy.enjoytrip.hotPlace.model.dto.HotPlaceArticle;
import com.ssafy.enjoytrip.hotPlace.model.dto.HotPlaceTag;

import java.util.List;

public interface HotPlaceService {

    List<HotPlace> selectAllHotPlace();

    List<HotPlaceArticle> selectAllHotPlaceArticle(String hotPlaceId);

    HotPlace selectHotPlaceByHotPlaceId(String hotPlaceId);

    HotPlaceArticle selectHotPlaceArticleByArticleId(int hotPlaceId);

    int updateHotPlaceArticleImage(int hotPlaceArticleId, String imageUrl);

    int increaseHitHotPlaceCount(String hotPlaceId);

    int decreaseHitHotPlaceCount(String hotPlaceId);

    int insertHotPlace(HotPlace hotPlace);

    int insertHotPlaceArticle(HotPlaceArticle hotPlaceArticle);

    int updateHotPlaceTag(String hotPlaceId, String tagId);

    void updateHotPlaceTagList(String hotPlaceId, List<String> tagIdList);

    int insertHotPlaceTag(String hotPlaceId, String tagName);

    void insertHotPlaceTagList(String hotPlaceId, List<String> tagIdList);

    List<HotPlaceTag> selectHotPlaceTagList(String hotPlaceId);

    List<HotPlace> selectHotPlaceByKeyword(String keyword);
}
