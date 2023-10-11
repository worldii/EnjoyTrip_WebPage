package com.ssafy.enjoytrip.core.hotplace.model.mapper;

import com.ssafy.enjoytrip.core.hotplace.model.entity.HotPlaceTag;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HotPlaceTagMapper {

    void insertTags(final List<HotPlaceTag> tags);

    Optional<HotPlaceTag> findById(final Long hotPlaceTagId);

    int increaseTagCount(final Long hotPlaceTagId);
}
