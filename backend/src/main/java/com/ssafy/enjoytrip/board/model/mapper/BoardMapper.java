package com.ssafy.enjoytrip.board.model.mapper;

import com.github.pagehelper.Page;
import com.ssafy.enjoytrip.board.model.dto.Board;
import com.ssafy.enjoytrip.board.model.dto.FileInfo;
import com.ssafy.enjoytrip.board.model.dto.request.SearchDto;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardMapper {

    Long insertBoard(final Board board);

    Optional<Board> selectBoard(final int boardId);

    Page<Board> selectAll();

    Page<Board> selectBoardListBySearchDto(final SearchDto searchDto);

    void updateBoard(final Board board);

    void updateHit(final int boardId);

    void deleteBoard(final int boardId);

    List<FileInfo> selectFile(final Long boardId);

    int insertFile(final Long boardId, final List<FileInfo> imageFiles);
}
