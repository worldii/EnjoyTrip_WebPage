package com.ssafy.enjoytrip.board.service;

import com.ssafy.enjoytrip.board.model.dto.Comment;
import com.ssafy.enjoytrip.board.model.dto.request.CommentModifyRequest;
import com.ssafy.enjoytrip.board.model.dto.request.CommentSaveRequest;
import com.ssafy.enjoytrip.board.model.dto.response.CommentResponse;
import com.ssafy.enjoytrip.board.model.mapper.BoardMapper;
import com.ssafy.enjoytrip.board.model.mapper.CommentMapper;
import com.ssafy.enjoytrip.global.error.BoardException;
import com.ssafy.enjoytrip.global.error.CommentNotFoundException;
import com.ssafy.enjoytrip.user.model.dto.User;
import com.ssafy.enjoytrip.user.model.mapper.UserMapper;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentMapper commentMapper;
    private final BoardMapper boardMapper;
    private final UserMapper userMapper;

    @Override
    public Long save(
        final CommentSaveRequest commentSaveRequest,
        final String userId, final Long boardId
    ) {
        boardMapper.selectBoard(boardId)
            .orElseThrow(() -> new BoardException("해당 boardId에 해당하는 board가 없습니다."));
        User user = userMapper.selectByUserId(userId)
            .orElseThrow(() -> new BoardException("해당 userId에 해당하는 user가 없습니다."));

        validateSameUser(user.getUserId(), userId);

        Comment comment = Comment.builder()
            .content(commentSaveRequest.getContent())
            .boardId(boardId)
            .userId(userId)
            .build();

        return commentMapper.insertComment(comment);
    }

    private void validateSameUser(final String commentUserId, final String userId) {
        if (commentUserId.equals(userId)) {
            throw new BoardException("유저 아이디가 다릅니다");
        }
    }

    @Override
    public List<CommentResponse> getCommentList(final Long boardId) {
        boardMapper.selectBoard(boardId)
            .orElseThrow(() -> new BoardException("해당 boardId에 해당하는 board가 없습니다."));

        return commentMapper.selectAll(boardId)
            .stream()
            .map(CommentResponse::from)
            .collect(Collectors.toList());
    }

    @Override
    public Comment detail(final Long commentId) {
        return commentMapper
            .selectComment(commentId)
            .orElseThrow(() -> new CommentNotFoundException("해당 commentId에 해당하는 comment가 없습니다."));
    }


    @Override
    public void modify(Long commentId, CommentModifyRequest request) {
        boardMapper.selectBoard(request.getBoardId())
            .orElseThrow(() -> new BoardException("해당 boardId에 해당하는 board가 없습니다."));
        Comment comment = commentMapper
            .selectComment(commentId)
            .orElseThrow(() -> new BoardException("해당 commentId에 해당하는 comment가 없습니다."));

        validateSameUser(comment.getUserId(), request.getUserId());

        Comment newComment = Comment.builder()
            .commentId(commentId)
            .content(request.getContent())
            .boardId(request.getBoardId())
            .userId(request.getUserId())
            .build();

        commentMapper.updateComment(newComment);
    }

    @Override
    public void delete(final Long commentId) {
        commentMapper.deleteComment(commentId);
    }
}
