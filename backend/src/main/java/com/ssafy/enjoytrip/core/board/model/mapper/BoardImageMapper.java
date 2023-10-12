package com.ssafy.enjoytrip.core.board.model.mapper;

import com.ssafy.enjoytrip.core.board.model.entity.BoardImageInfo;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BoardImageMapper {

    void insertFile(@Param("boardId") final Long boardId,
        @Param("imageFiles") final List<BoardImageInfo> imageFiles);

    List<BoardImageInfo> selectFile(final Long boardId);

    void deleteFileByBoardId(final Long boardId);
}
