package com.ssafy.enjoytrip.board.model.dao;

import com.github.pagehelper.Page;
import com.ssafy.enjoytrip.board.model.dto.request.SearchDto;
import com.ssafy.enjoytrip.board.model.entity.Board;
import com.ssafy.enjoytrip.board.model.mapper.BoardMapper;
import com.ssafy.enjoytrip.media.model.entity.FileInfo;
import java.util.List;
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

    public Page<Board> selectBoardListBySearchDto(final SearchDto searchDto) {
        return boardMapper.selectBoardListBySearchDto(searchDto);
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

    public List<FileInfo> selectFile(final Long boardId) {
        return boardMapper.selectFile(boardId);
    }

    @Transactional
    public int insertFile(final Long boardId, final List<FileInfo> imageFiles) {
        return boardMapper.insertFile(boardId, imageFiles);
    }
}
