package com.ssafy.enjoytrip.board.service;


import com.ssafy.enjoytrip.board.model.dto.BoardRequestDto;
import com.ssafy.enjoytrip.board.model.dto.Comment;
import com.ssafy.enjoytrip.board.model.dto.CommentRequestDto;
import com.ssafy.enjoytrip.board.model.dto.CommentResponseDto;

import java.util.List;

public interface CommentService {
    Comment detail(int commentId);
    int regist(CommentRequestDto commentRequestDto, String userId, int boardId);
    int modify(int commentId, CommentRequestDto commentRequestDto);
    int delete(int commentId);
    List<CommentResponseDto> getCommentList(int boardId);
}
