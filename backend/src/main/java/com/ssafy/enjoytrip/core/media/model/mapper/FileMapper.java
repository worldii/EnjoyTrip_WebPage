package com.ssafy.enjoytrip.core.media.model.mapper;

import com.ssafy.enjoytrip.core.media.model.entity.FileInfo;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileMapper {

    void insertFile(final Long boardId, final List<FileInfo> imageFiles);

    List<FileInfo> selectFile(final Long boardId);

    void deleteFileByBoardId(final Long boardId);
}
