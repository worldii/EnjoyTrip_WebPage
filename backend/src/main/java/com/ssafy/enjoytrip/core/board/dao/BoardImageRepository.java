package com.ssafy.enjoytrip.core.board.dao;


import com.ssafy.enjoytrip.core.board.model.entity.BoardImageInfo;
import com.ssafy.enjoytrip.core.board.model.mapper.BoardImageMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardImageRepository {

    private final BoardImageMapper boardImageMapper;

    @Transactional
    public void insertFile(final Long boardId, final List<BoardImageInfo> imageFiles) {
        boardImageMapper.insertFile(boardId, imageFiles);
    }

    public List<BoardImageInfo> selectFileByBoardId(final Long boardId) {
        return boardImageMapper.selectFile(boardId);
    }

    @Transactional
    public void deleteFileByBoardId(final Long boardId) {
        boardImageMapper.deleteFileByBoardId(boardId);
    }
}
