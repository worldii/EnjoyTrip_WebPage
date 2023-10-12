package com.ssafy.enjoytrip.core.hotplace.model.mapper;

import com.ssafy.enjoytrip.core.hotplace.model.entity.HotPlaceArticleImageInfo;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface HotPlaceArticleImageMapper {

    void insertFile(
        @Param("hotPlaceArticleId") final Long hotPlaceArticleId,
        @Param("imageFiles") final List<HotPlaceArticleImageInfo> imageFiles
    );

    List<HotPlaceArticleImageInfo> selectFile(final Long hotPlaceArticleId);

    void deleteFileByHotPlaceArticleId(final Long hotPlaceArticleId);
}
