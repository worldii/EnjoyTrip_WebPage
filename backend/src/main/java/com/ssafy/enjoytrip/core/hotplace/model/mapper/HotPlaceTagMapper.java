package com.ssafy.enjoytrip.core.hotplace.model.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HotPlaceTagMapper {

    int updateHotPlaceTag(String hotPlaceId, String tagName);

    int insertHotPlaceTag(String hotPlaceId, String tagName);

    Long selectHotPlaceTagIdByTagNameAndHotPlaceId(final String tagName, final String hotPlaceId);
}
