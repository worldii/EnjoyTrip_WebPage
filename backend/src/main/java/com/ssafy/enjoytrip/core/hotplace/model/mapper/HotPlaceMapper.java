package com.ssafy.enjoytrip.core.hotplace.model.mapper;

import com.github.pagehelper.Page;
import com.ssafy.enjoytrip.core.hotplace.model.entity.HotPlace;
import com.ssafy.enjoytrip.core.hotplace.model.entity.HotPlaceArticle;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HotPlaceMapper {

    int insertHotPlace(final HotPlace hotPlace);

    Page<HotPlace> selectAllHotPlace(final String keyword);

    Optional<HotPlace> selectAllByHotPlaceId(final String hotPlaceId);

    Optional<HotPlace> selectHotPlaceByHotPlaceId(final String hotPlaceId);

    Optional<HotPlaceArticle> selectHotPlaceArticleByArticleId(final Long articleId);

    void updateHotPlace(HotPlace hotPlace);

    int updateHotPlaceArticleImage(final int hotPlaceArticleId, String imageUrl);

    int updateHotPlaceTag(final String hotPlaceId, final String tagName);

    int insertHotPlaceTag(final String hotPlaceId, final String tagName);

    Long selectHotPlaceTagIdByTagName(final String tagName);
}
