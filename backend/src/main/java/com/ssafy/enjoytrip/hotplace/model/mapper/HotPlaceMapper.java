package com.ssafy.enjoytrip.hotplace.model.mapper;

import com.ssafy.enjoytrip.hotplace.model.entity.HotPlace;
import com.ssafy.enjoytrip.hotplace.model.entity.HotPlaceArticle;
import com.ssafy.enjoytrip.hotplace.model.entity.HotPlaceTag;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HotPlaceMapper {

    List<HotPlace> selectAllHotPlace();

    List<HotPlaceArticle> selectAllHotPlaceArticle(final String hotPlaceId);

    Optional<HotPlace> selectHotPlaceByHotPlaceId(final String hotPlaceId);

    Optional<HotPlaceArticle> selectHotPlaceArticleByArticleId(final int articleId);

    int insertHotPlace(final HotPlace hotPlace);

    int insertHotPlaceArticle(final HotPlaceArticle hotPlaceArticle);

    int updateHotPlaceArticleImage(final int hotPlaceArticleId, String imageUrl);

    int increaseHitHotPlaceCount(final String hotPlaceId);

    int decreaseHitHotPlaceCount(final String hotPlaceId);

    int updateHotPlaceTag(final String hotPlaceId, final String tagName);

    int insertHotPlaceTag(final String hotPlaceId, final String tagName);

    List<HotPlaceTag> selectHotPlaceTagList(final String hotPlaceId);

    List<HotPlace> selectHotPlaceByKeyword(final String keyword);
}
