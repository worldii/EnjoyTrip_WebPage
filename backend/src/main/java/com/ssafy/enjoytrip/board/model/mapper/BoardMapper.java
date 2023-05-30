package com.ssafy.enjoytrip.board.model.mapper;

import com.github.pagehelper.Page;
import com.ssafy.enjoytrip.board.model.dto.Board;
import com.ssafy.enjoytrip.board.model.dto.FileInfo;
import com.ssafy.enjoytrip.board.model.dto.SearchDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface BoardMapper {
    // ToDo 게시판 CRUD
    Optional<Board> selectBoard(int boardId);
    int insertBoard(Board board);
    int updateBoard(Board board);
    int deleteBoard(int boardId);
    int updateHit(int boardId);
    Page<Board> selectAll();
    Page<Board> selectBoardListBySearchDto(SearchDto searchDto);

    int insertFile(int boardId, List<FileInfo> imageFiles);

    List<FileInfo> selectFile(int boardId);

}
