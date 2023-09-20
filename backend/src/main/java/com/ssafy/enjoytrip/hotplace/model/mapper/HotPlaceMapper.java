package com.ssafy.enjoytrip.hotplace.model.mapper;

import com.ssafy.enjoytrip.hotplace.model.HotPlace;
import com.ssafy.enjoytrip.hotplace.model.dto.HotPlaceArticle;
import com.ssafy.enjoytrip.hotplace.model.dto.HotPlaceTag;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

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
