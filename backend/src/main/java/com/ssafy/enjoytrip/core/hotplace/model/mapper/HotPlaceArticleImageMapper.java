package com.ssafy.enjoytrip.core.hotplace.model.mapper;

import com.ssafy.enjoytrip.core.hotplace.model.entity.HotPlaceArticleImage;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface HotPlaceArticleImageMapper {

    void insertFile(@Param("imageFiles") final List<HotPlaceArticleImage> imageFiles);

    List<HotPlaceArticleImage> selectFile(final Long hotPlaceArticleId);

    void deleteFileByHotPlaceArticleId(final Long hotPlaceArticleId);
}
