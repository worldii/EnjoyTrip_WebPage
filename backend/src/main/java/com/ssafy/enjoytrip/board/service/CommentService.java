package com.ssafy.enjoytrip.board.service;


import com.ssafy.enjoytrip.board.model.dto.request.CommentModifyRequest;
import com.ssafy.enjoytrip.board.model.dto.request.CommentSaveRequest;
import com.ssafy.enjoytrip.board.model.dto.response.CommentResponse;
import com.ssafy.enjoytrip.board.model.entity.Comment;
import java.util.List;

public interface CommentService {

    Long save(final CommentSaveRequest commentSaveRequest, final String userId, final Long boardId);

    Comment detail(final Long commentId);

    void modify(final Long commentId, final String userId, final CommentModifyRequest request);

    void delete(final Long commentId, final String userId);

    List<CommentResponse> getCommentList(final Long boardId);
}
