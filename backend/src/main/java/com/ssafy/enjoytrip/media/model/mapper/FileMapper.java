package com.ssafy.enjoytrip.media.model.mapper;

import com.ssafy.enjoytrip.media.model.entity.FileInfo;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileMapper {

    void insertFile(Long boardId, List<FileInfo> list);

    List<FileInfo> selectFile(Long boardId);

    void deleteFile(Long boardId);
}
