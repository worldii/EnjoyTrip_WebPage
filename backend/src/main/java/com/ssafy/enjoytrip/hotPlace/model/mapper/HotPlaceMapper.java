package com.ssafy.enjoytrip.hotPlace.model.mapper;

import com.ssafy.enjoytrip.hotPlace.model.dto.HotPlace;
import com.ssafy.enjoytrip.hotPlace.model.dto.HotPlaceArticle;
import com.ssafy.enjoytrip.hotPlace.model.dto.HotPlaceTag;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HotPlaceMapper {
    List<HotPlace> selectAllHotPlace();
    List<HotPlaceArticle> selectAllHotPlaceArticle(String hotPlaceId);
    HotPlace selectHotPlaceByHotPlaceId(String hotPlaceId);
    HotPlaceArticle selectHotPlaceArticleByArticleId(int articleId);

    int insertHotPlace(HotPlace hotPlace);
    int insertHotPlaceArticle(HotPlaceArticle hotPlaceArticle);

    int updateHotPlaceArticleImage(int hotPlaceArticleId, String imageUrl);
    int increaseHitHotPlaceCount(String hotPlaceId);
    int decreaseHitHotPlaceCount(String hotPlaceId);

    int updateHotPlaceTag(String hotPlaceId, String tagName);
    int insertHotPlaceTag(String hotPlaceId, String tagName);
    List<HotPlaceTag> selectHotPlaceTagList(String hotPlaceId);

    List<HotPlace> selectHotPlaceByKeyword(String keyword);
}
