package com.ssafy.enjoytrip.core.hotplace.model.mapper;

import com.ssafy.enjoytrip.core.hotplace.model.entity.HotPlaceArticle;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HotPlaceArticleMapper {

    Long insertHotPlaceArticle(final HotPlaceArticle hotPlaceArticle);

    Optional<HotPlaceArticle> selectHotPlaceArticleByArticleId(Long hotPlaceArticleId);

    int updateHotPlaceArticleImage(int hotPlaceArticleId, String imageUrl);
}
