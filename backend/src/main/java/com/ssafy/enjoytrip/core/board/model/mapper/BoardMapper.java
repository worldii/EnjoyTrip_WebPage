package com.ssafy.enjoytrip.core.board.model.mapper;

import com.github.pagehelper.Page;
import com.ssafy.enjoytrip.core.board.model.dto.request.SearchDto;
import com.ssafy.enjoytrip.core.board.model.entity.Board;
import com.ssafy.enjoytrip.core.media.model.entity.FileInfo;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardMapper {

    Long insertBoard(final Board board);

    Optional<Board> selectBoard(final Long boardId);

    Page<Board> selectAll();

    Page<Board> selectBoardListBySearchDto(final SearchDto searchDto);

    void updateBoard(final Board board);

    void updateHit(final Long boardId);

    void deleteBoard(final Long boardId);

    List<FileInfo> selectFile(final Long boardId);

    int insertFile(final Long boardId, final List<FileInfo> imageFiles);
}
