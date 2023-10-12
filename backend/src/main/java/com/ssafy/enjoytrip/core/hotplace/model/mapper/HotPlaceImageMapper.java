package com.ssafy.enjoytrip.core.hotplace.model.mapper;

import com.ssafy.enjoytrip.core.hotplace.model.entity.HotPlaceImageInfo;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface HotPlaceImageMapper {

    void insertFile(
        @Param("hotPlaceId") final String hotPlaceId,
        @Param("imageFiles") final List<HotPlaceImageInfo> imageFiles);

    List<HotPlaceImageInfo> selectFile(final String hotPlaceId);

    void deleteFileByHotPlaceId(final String hotPlaceId);
}
