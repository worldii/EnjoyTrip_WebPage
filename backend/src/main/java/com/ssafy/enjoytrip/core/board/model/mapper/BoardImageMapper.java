package com.ssafy.enjoytrip.core.board.model.mapper;

import com.ssafy.enjoytrip.core.board.model.entity.BoardImageInfo;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardImageMapper {

    void insertFile(final Long boardId, final List<BoardImageInfo> imageFiles);

    List<BoardImageInfo> selectFile(final Long boardId);

    void deleteFileByBoardId(final Long boardId);
}
