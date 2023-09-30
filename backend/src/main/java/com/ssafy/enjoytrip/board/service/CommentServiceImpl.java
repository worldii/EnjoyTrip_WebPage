package com.ssafy.enjoytrip.board.service;

import com.ssafy.enjoytrip.board.dao.BoardRepository;
import com.ssafy.enjoytrip.board.dao.CommentRepository;
import com.ssafy.enjoytrip.board.model.dto.request.CommentModifyRequest;
import com.ssafy.enjoytrip.board.model.dto.request.CommentSaveRequest;
import com.ssafy.enjoytrip.board.model.dto.response.CommentResponse;
import com.ssafy.enjoytrip.board.model.entity.Board;
import com.ssafy.enjoytrip.board.model.entity.Comment;
import com.ssafy.enjoytrip.global.error.BoardException;
import com.ssafy.enjoytrip.global.error.CommentNotFoundException;
import com.ssafy.enjoytrip.user.dao.UserRepository;
import com.ssafy.enjoytrip.user.model.entity.User;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public Long save(
        final CommentSaveRequest commentSaveRequest,
        final String userId, final Long boardId
    ) {
        Board board = boardRepository.selectBoard(boardId)
            .orElseThrow(() -> new BoardException("해당 boardId에 해당하는 board가 없습니다."));
        final User user = userRepository.selectByUserId(userId)
            .orElseThrow(() -> new BoardException("해당 userId에 해당하는 user가 없습니다."));

        validateSameUser(user.getUserId(), userId);

        final Comment comment = Comment.builder()
            .content(commentSaveRequest.getContent())
            .boardId(board.getBoardId())
            .userId(userId)
            .build();

        return commentRepository.insertComment(comment);
    }

    private void validateSameUser(final String commentUserId, final String userId) {
        if (commentUserId.equals(userId)) {
            throw new BoardException("유저 아이디가 다릅니다");
        }
    }

    @Override
    public List<CommentResponse> getCommentList(final Long boardId) {
        Board board = boardRepository.selectBoard(boardId)
            .orElseThrow(() -> new BoardException("해당 boardId에 해당하는 board가 없습니다."));

        return commentRepository.selectAll(board.getBoardId())
            .stream()
            .map(CommentResponse::from)
            .collect(Collectors.toList());
    }

    @Override
    public Comment detail(final Long commentId) {
        return commentRepository
            .selectComment(commentId)
            .orElseThrow(() -> new CommentNotFoundException("해당 commentId에 해당하는 comment가 없습니다."));
    }


    @Override
    @Transactional
    public void modify(final Long commentId, final CommentModifyRequest request) {
        Board board = boardRepository.selectBoard(request.getBoardId())
            .orElseThrow(() -> new BoardException("해당 boardId에 해당하는 board가 없습니다."));

        final Comment comment = commentRepository
            .selectComment(commentId)
            .orElseThrow(() -> new BoardException("해당 commentId에 해당하는 comment가 없습니다."));

        validateSameUser(comment.getUserId(), request.getUserId());

        final Comment newComment = Comment.builder()
            .commentId(commentId)
            .content(request.getContent())
            .boardId(board.getBoardId())
            .userId(request.getUserId())
            .build();

        commentRepository.updateComment(newComment);
    }

    @Override
    @Transactional
    public void delete(final Long commentId) {
        commentRepository.deleteComment(commentId);
    }
}
