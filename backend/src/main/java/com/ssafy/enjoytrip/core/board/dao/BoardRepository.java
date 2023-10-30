package com.ssafy.enjoytrip.core.board.dao;


import com.github.pagehelper.Page;
import com.ssafy.enjoytrip.core.board.model.dto.request.BoardSearchRequest;
import com.ssafy.enjoytrip.core.board.model.entity.Board;
import com.ssafy.enjoytrip.core.board.model.mapper.BoardMapper;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardRepository {

    private final BoardMapper boardMapper;

    @Transactional
    public Long insertBoard(final Board board) {
        return boardMapper.insertBoard(board);
    }

    public Optional<Board> selectBoard(final Long boardId) {
        return boardMapper.selectBoard(boardId);
    }

    public Page<Board> selectAll() {
        return boardMapper.selectAll();
    }

    public Page<Board> selectBoardList(final BoardSearchRequest boardSearchRequest) {
        return boardMapper.selectBoardListBySearchDto(boardSearchRequest);
    }

    @Transactional
    public void updateBoard(final Board board) {
        boardMapper.updateBoard(board);
    }

    @Transactional
    public void updateHit(final Long boardId) {
        boardMapper.updateHit(boardId);
    }

    @Transactional
    public void deleteBoard(final Long boardId) {
        boardMapper.deleteBoard(boardId);
    }
}
