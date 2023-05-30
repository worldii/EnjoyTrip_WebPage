package com.ssafy.enjoytrip.board.service;

import com.github.pagehelper.Page;
import com.ssafy.enjoytrip.board.model.dto.Board;
import com.ssafy.enjoytrip.board.model.dto.BoardRequestDto;
import com.ssafy.enjoytrip.board.model.dto.SearchDto;


public interface BoardService {
    Board detail(int boardId);

    int regist(BoardRequestDto boardRequestDto, String userId);

    int modify(int boardId, BoardRequestDto boardRequestDto);

    int delete(int boardId);

    int updateHit(int boardId);

    Page<Board> getBoardList();

    Page<Board> getBoardListBySearchDto(SearchDto searchDto);
}
