package com.ssafy.enjoytrip.board.service;

import com.github.pagehelper.Page;
import com.ssafy.enjoytrip.board.model.dto.Board;
import com.ssafy.enjoytrip.board.model.dto.BoardRequestDto;
import com.ssafy.enjoytrip.board.model.dto.Comment;
import com.ssafy.enjoytrip.board.model.dto.SearchDto;
import com.ssafy.enjoytrip.board.model.mapper.BoardMapper;
import com.ssafy.enjoytrip.board.model.mapper.CommentMapper;
import com.ssafy.enjoytrip.error.BoardNotFoundException;
import com.ssafy.enjoytrip.error.UserNotFoundException;
import com.ssafy.enjoytrip.user.model.dto.User;
import com.ssafy.enjoytrip.user.model.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Slf4j
public class BoardServiceImpl implements BoardService {

    private final BoardMapper boardMapper;
    private final UserMapper userMapper;
    private final CommentMapper commentMapper;

    @Transactional(readOnly = true)
    @Override
    public Board detail(int boardId) {
        log.info("boardId : {}", boardId);
        return boardMapper.selectBoard(boardId).orElseThrow(() -> new BoardNotFoundException("해당 boardId에 해당하는 board가 없습니다."));
    }

    @Override
    @Transactional
    public int regist(BoardRequestDto boardRequestDto, String userId) {
        Board board = new Board(boardRequestDto, userId);
        boardMapper.insertBoard(board);
        return board.getBoardId();
    }

    @Override
    @Transactional
    public int modify(int boardId, String userId, BoardRequestDto boardRequestDto) {
        boardRequestDto.setBoardId(boardId);
        User user = userMapper.selectByUserId(userId);
        Board board = boardMapper.selectBoard(boardId).orElseThrow(() -> new BoardNotFoundException("해당 boardId에 해당하는 board가 없습니다."));
        if (user == null) throw new RuntimeException("해당 유저가 없습니다.");
        if (user.getAuthority() == 1) {
            if (!board.getUserId().equals(user.getUserId())) throw new UserNotFoundException("해당 유저가 아닙니다.");
        }
        return boardMapper.updateBoard(boardRequestDto.toEntity());
    }

    @Override
    @Transactional
    public int delete(int boardId, String userId) {
        log.info("userId : {}", userId);
        User user = userMapper.selectByUserId(userId);
        Board board = boardMapper.selectBoard(boardId).orElseThrow(() -> new BoardNotFoundException("해당 boardId에 해당하는 board가 없습니다."));
        log.info("user : {}", user);
        if (user == null) throw new RuntimeException("해당 유저가 없습니다.");
        if (user.getAuthority() == 1) {
            if (!board.getUserId().equals(user.getUserId())) throw new UserNotFoundException("해당 유저가 아닙니다.");
        }
        commentMapper.deleteAll(boardId);
        return boardMapper.deleteBoard(boardId);
    }

    @Override
    @Transactional
    public int updateHit(int boardId) {
        return boardMapper.updateHit(boardId);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Board> getBoardList() {
        return boardMapper.selectAll();
    }

    @Override
    public Page<Board> getBoardListBySearchDto(SearchDto searchDto) {
        return boardMapper.selectBoardListBySearchDto(searchDto);
    }


}
