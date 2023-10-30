package com.ssafy.enjoytrip.core.board.model.mapper;


import com.github.pagehelper.Page;
import com.ssafy.enjoytrip.core.board.model.dto.request.BoardSearchRequest;
import com.ssafy.enjoytrip.core.board.model.entity.Board;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardMapper {

    Long insertBoard(final Board board);

    Optional<Board> selectBoard(final Long boardId);

    Page<Board> selectAll();

    Page<Board> selectBoardListBySearchDto(final BoardSearchRequest boardSearchRequest);

    void updateBoard(final Board board);

    void updateHit(final Long boardId);

    void deleteBoard(final Long boardId);
}
